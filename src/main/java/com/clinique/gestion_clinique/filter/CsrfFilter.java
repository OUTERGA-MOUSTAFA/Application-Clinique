package com.clinique.gestion_clinique.filter;

import jakarta.servlet.*;
import java.io.IOException;
import java.util.Objects;

/** Vérifie le jeton CSRF des requêtes POST. */
@jakarta.servlet.annotation.WebFilter("/*")
public class CsrfFilter extends jakarta.servlet.http.HttpFilter implements Filter {

    @Override
    public void doFilter(jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!"POST".equals(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        jakarta.servlet.http.HttpSession session = request.getSession(false);
        String expectedToken = session != null ? (String) session.getAttribute("csrfToken") : null;
        String receivedToken = request.getParameter("_csrf");

        if (expectedToken != null && receivedToken != null && Objects.equals(expectedToken, receivedToken)) {
            chain.doFilter(request, response);
            return;
        }

        response.sendError(jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN, "CSRF token invalide");
    }
}