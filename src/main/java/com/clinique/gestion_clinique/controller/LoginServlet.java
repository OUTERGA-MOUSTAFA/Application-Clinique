package com.clinique.gestion_clinique.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import com.clinique.gestion_clinique.config.ConnectionManager;
import com.clinique.gestion_clinique.entity.Utilisateur;
import com.clinique.gestion_clinique.repository.jdbc.JdbcUtilisateurDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // JSP
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        JdbcUtilisateurDAO utilisateurDAO = new JdbcUtilisateurDAO(ConnectionManager.getDataSource());
        Optional<Utilisateur> resultat = utilisateurDAO.findByEmail(email);

        if (resultat.isEmpty()) {
            request.setAttribute("erreur", "Identifiants invalides");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        Utilisateur utilisateur = resultat.get();
        if (org.mindrot.jbcrypt.BCrypt.checkpw(motDePasse, utilisateur.getMotDePasse())) {
            request.getSession().setAttribute("utilisateur", utilisateur);
            String role = utilisateur.getRole().name();
            request.getSession().setAttribute("role", role);

            if ("INFIRMIER".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/infirmier/patients");
            } else {
                response.sendRedirect(request.getContextPath() + "/generaliste/patients");
            }
            return;
        }

        request.setAttribute("erreur", "Identifiants invalides");
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
}