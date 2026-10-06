package com.clinique.gestion_clinique.api.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

// import org.springframework.stereotype.Repository;


import com.clinique.gestion_clinique.api.model.Utilisateur;

public class UtilisateurRepository {

    @PersistenceContext 
    private EntityManager em;

    // public UtilisateurRepository(){}
    public UtilisateurRepository(EntityManager em){
        this.em = em;
    }

    public Utilisateur findByEmail(String email) {
        try {
            return em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.email = :email",
                    Utilisateur.class
            )
            .setParameter("email", email)
            .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }
    public Utilisateur  findById(Long id) {

        try {

            return em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.id = :id",
                    Utilisateur.class
            )
            .setParameter("id", id)
            .getSingleResult();

        } catch (NoResultException e) {

            return null;
        }
    }

}
