package com.clinique.gestion_clinique.api.security;
import java.security.Principal;

import com.clinique.gestion_clinique.api.model.Utilisateur;

import jakarta.ws.rs.core.SecurityContext;
public class SecurityUserContext implements SecurityContext{
    
    private final Utilisateur utilisateur;
    private final boolean secure;

     public SecurityUserContext(Utilisateur utilisateur, boolean secure) {
        this.utilisateur = utilisateur;
        this.secure = secure;
    }


     @Override
    public Principal getUserPrincipal() {

        return () -> utilisateur.getEmail();
    }

    @Override
    public boolean isUserInRole(String role) {

        return utilisateur.getRole()
                .name()
                .equals(role);
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
}
