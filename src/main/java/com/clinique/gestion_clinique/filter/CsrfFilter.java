// package com.clinique.gestion_clinique.filter;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.annotation.WebFilter;
// import jakarta.servlet.http.HttpFilter;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import jakarta.servlet.http.HttpSession;

// import java.io.IOException;
// import java.util.Objects;

// @WebFilter("/*")
// public class CsrfFilter extends HttpFilter {

//     @Override
//     protected void doFilter(
//             HttpServletRequest request,
//             HttpServletResponse response,
//             FilterChain chain) throws IOException, ServletException {

//         // GET لا يحتاج CSRF validation
//         if (!"POST".equalsIgnoreCase(request.getMethod())) {
//             chain.doFilter(request, response);
//             return;
//         }

//         HttpSession session = request.getSession(false);

//         if (session == null) {
//             response.sendError(
//                     HttpServletResponse.SC_FORBIDDEN,
//                     "Session inexistante");
//             return;
//         }

//         String expectedToken = (String) session.getAttribute("csrfToken");

//         String receivedToken = request.getParameter("_csrf");

//         if (expectedToken == null
//                 || receivedToken == null
//                 || !Objects.equals(expectedToken, receivedToken)) {

//             response.sendError(
//                     HttpServletResponse.SC_FORBIDDEN,
//                     "CSRF token invalide");
//             return;
//         }

//         chain.doFilter(request, response);
//     }
// }


package com.clinique.gestion_clinique.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;

@WebFilter("/*")
public class CsrfFilter extends HttpFilter {

    @Override
    protected void doFilter(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        String path = request.getRequestURI()
                .substring(request.getContextPath().length());

        // Login POST doit pouvoir vérifier son token CSRF.
        // Si aucune session n'existe, on laisse le LoginServlet
        // gérer la création de session.
        if (path.equals("/login")
                && "POST".equalsIgnoreCase(request.getMethod())) {

            HttpSession session = request.getSession(false);

            if (session == null) {
                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "Session inexistante");
                return;
            }

            String expectedToken =
                    (String) session.getAttribute("csrfToken");

            String receivedToken =
                    request.getParameter("_csrf");

            if (expectedToken == null
                    || receivedToken == null
                    || !Objects.equals(expectedToken, receivedToken)) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "CSRF token invalide");
                return;
            }

            chain.doFilter(request, response);
            return;
        }

        // GET, PUT, DELETE... ne sont pas vérifiés ici pour le moment.
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Session inexistante");
            return;
        }

        String expectedToken =
                (String) session.getAttribute("csrfToken");

        String receivedToken =
                request.getParameter("_csrf");

        if (expectedToken == null
                || receivedToken == null
                || !Objects.equals(expectedToken, receivedToken)) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "CSRF token invalide");
            return;
        }

        chain.doFilter(request, response);
    }
}