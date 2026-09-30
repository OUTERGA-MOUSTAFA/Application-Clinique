package com.clinique.gestion_clinique.filter;

import jakarta.servlet.*;
import java.io.IOException;

/** Filtre les requêtes selon la session et le rôle de l'utilisateur. */
@jakarta.servlet.annotation.WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        jakarta.servlet.http.HttpServletRequest request = (jakarta.servlet.http.HttpServletRequest) servletRequest;
        jakarta.servlet.http.HttpServletResponse response = (jakarta.servlet.http.HttpServletResponse) servletResponse;
        String contextPath = request.getContextPath();
        String path = request.getRequestURI().substring(contextPath.length());

        if (path.equals("/login") || path.equals("/logout") || path.startsWith("/css/")
                || path.startsWith("/js/") || path.startsWith("/images/") || path.equals("/favicon.ico")) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        jakarta.servlet.http.HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(contextPath + "/login");
            return;
        }

        String role = (String) session.getAttribute("role");
        if ((path.startsWith("/infirmier/") && !"INFIRMIER".equals(role))
                || (path.startsWith("/generaliste/") && !"GENERALISTE".equals(role))) {
            response.sendError(jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        chain.doFilter(servletRequest, servletResponse);
    }
}