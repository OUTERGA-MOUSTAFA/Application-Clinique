package com.clinique.gestion_clinique.controller;

import com.clinique.gestion_clinique.config.AppConfig;
import com.clinique.gestion_clinique.entity.Utilisateur;
import com.clinique.gestion_clinique.repository.UtilisateurDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UtilisateurDAO utilisateurDAO;

    @Override
    public void init() {
        utilisateurDAO = AppConfig.utilisateurDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // Création du token CSRF pour la session
        HttpSession session = request.getSession();

        if (session.getAttribute("csrfToken") == null) {
            session.setAttribute("csrfToken", UUID.randomUUID().toString());
        }

         request.getRequestDispatcher("/WEB-INF/views/login.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        if (email == null || email.isBlank()
                || motDePasse == null || motDePasse.isBlank()) {

            request.setAttribute("erreur",
                    "Email et mot de passe sont obligatoires.");

            request.getRequestDispatcher(
                    "/WEB-INF/views/login.jsp").forward(request, response);

            return;
        }

        Optional<Utilisateur> resultat = utilisateurDAO.findByEmail(email.trim());

        if (resultat.isEmpty()) {

            request.setAttribute("erreur",
                    "Identifiants invalides");

            request.getRequestDispatcher(
                    "/WEB-INF/views/login.jsp").forward(request, response);

            return;
        }

        Utilisateur utilisateur = resultat.get();

        boolean passwordCorrect;

        try {
            passwordCorrect = BCrypt.checkpw(
                    motDePasse,
                    utilisateur.getMotDePasse());
        } catch (IllegalArgumentException e) {
            passwordCorrect = false;
        }

        if (!passwordCorrect) {

            request.setAttribute("erreur",
                    "Identifiants invalides");

            request.getRequestDispatcher(
                    "/WEB-INF/views/login.jsp").forward(request, response);

            return;
        }

        // Authentification réussie
        HttpSession session = request.getSession();

        session.setAttribute("utilisateur", utilisateur);
        session.setAttribute(
                "role",
                utilisateur.getRole().name());

        // Redirection selon le rôle
        if (utilisateur.getRole().name().equals("INFIRMIER")) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/infirmier/patients");

        } else if (utilisateur.getRole().name().equals("GENERALISTE")) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/generaliste/patients");
        }
    }
}