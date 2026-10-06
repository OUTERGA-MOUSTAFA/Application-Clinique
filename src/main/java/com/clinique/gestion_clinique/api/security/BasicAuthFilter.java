package com.clinique.gestion_clinique.api.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.annotation.Priority;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import com.clinique.gestion_clinique.api.model.Utilisateur;
import com.clinique.gestion_clinique.api.repository.UtilisateurRepository;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class BasicAuthFilter implements ContainerRequestFilter {

        private final EntityManager em = Persistence.createEntityManagerFactory("cliniquePU")
                        .createEntityManager();

        private final UtilisateurRepository utilisateurRepository = new UtilisateurRepository(em);

        @Override
        public void filter(ContainerRequestContext requestContext) {

                // get Authorization: Basic bearer token
                String authorization = requestContext.getHeaderString("Authorization");

                // 1. Authorization header absent
                if (authorization == null ||
                                !authorization.startsWith("Basic ")) {

                        abortUnauthorized(requestContext);
                        return;
                }

                try {

                        // 2. Remove "Basic "
                        String encodedCredentials = authorization.substring("Basic ".length());

                        // 3. Decode Base64
                        String credentials = new String(
                                        Base64.getDecoder()
                                                        .decode(encodedCredentials),
                                        StandardCharsets.UTF_8);

                        // 4. username:password
                        String[] parts = credentials.split(":", 2);

                        if (parts.length != 2) {
                                abortUnauthorized(requestContext);
                                return;
                        }

                        String email = parts[0];
                        String password = parts[1];

                        // 5. Search user in database
                        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);

                        if (utilisateur == null) {
                                abortUnauthorized(requestContext);
                                return;
                        }

                        // 6. Verify password using BCrypt
                        boolean passwordCorrect = BCrypt.checkpw(
                                        password,
                                        utilisateur.getMotDePasse());

                        if (!passwordCorrect) {
                                abortUnauthorized(requestContext);
                                return;
                        }

                        // 7. Authentication successful
                        SecurityUserContext securityContext = new SecurityUserContext(
                                        utilisateur,
                                        requestContext.getUriInfo()
                                                        .getRequestUri()
                                                        .getScheme()
                                                        .equals("https"));

                        // 8. Put authenticated user into SecurityContext
                        requestContext.setSecurityContext(
                                        securityContext);

                } catch (Exception e) {

                        abortUnauthorized(requestContext);
                }
        }

        private void abortUnauthorized(
                        ContainerRequestContext requestContext) {

                requestContext.abortWith(
                                Response.status(Response.Status.UNAUTHORIZED)
                                                .header(
                                                                "WWW-Authenticate",
                                                                "Basic realm=\"Clinique API\"")
                                                .build());
        }
}
