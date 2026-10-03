package com.clinique.gestion_clinique.controller;

import com.clinique.gestion_clinique.config.AppConfig;
import com.clinique.gestion_clinique.entity.Utilisateur;
import com.clinique.gestion_clinique.repository.UtilisateurDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

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

                HttpSession session = request.getSession();

                if (session.getAttribute("csrfToken") == null) {
                        session.setAttribute(
                                        "csrfToken",
                                        UUID.randomUUID().toString());
                }

                request.getRequestDispatcher(
                                "/WEB-INF/views/login.jsp").forward(request, response);
        }

        @Override
        protected void doPost(
                        HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {

                String email = request.getParameter("email");
                String motDePasse = request.getParameter("motDePasse");

                Optional<Utilisateur> resultat = utilisateurDAO.findByEmail(email);

                if (resultat.isEmpty()) {
                        request.setAttribute(
                                        "erreur",
                                        "Identifiants invalides");

                        request.getRequestDispatcher(
                                        "/WEB-INF/views/login.jsp").forward(request, response);

                        return;
                }

                Utilisateur utilisateur = resultat.get();

                if (!BCrypt.checkpw(
                                motDePasse,
                                utilisateur.getMotDePasse())) {

                        request.setAttribute(
                                        "erreur",
                                        "Identifiants invalides");

                        request.getRequestDispatcher(
                                        "/WEB-INF/views/login.jsp").forward(request, response);

                        return;
                }

                HttpSession session = request.getSession();

                session.setAttribute("utilisateur", utilisateur);
                session.setAttribute(
                                "role",
                                utilisateur.getRole().name());

                String role = utilisateur.getRole().name();

                if ("INFIRMIER".equals(role)) {
                        response.sendRedirect(
                                        request.getContextPath()
                                                        + "/infirmier/patients");
                } else if ("GENERALISTE".equals(role)) {
                        response.sendRedirect(
                                        request.getContextPath()
                                                        + "/generaliste/patients");
                }
        }
}