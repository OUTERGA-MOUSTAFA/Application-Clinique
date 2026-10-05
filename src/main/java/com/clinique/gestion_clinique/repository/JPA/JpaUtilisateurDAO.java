package com.clinique.gestion_clinique.repository.JPA;

import com.clinique.gestion_clinique.config.JpaUtil;
import com.clinique.gestion_clinique.entity.Utilisateur;
import com.clinique.gestion_clinique.repository.UtilisateurDAO;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaUtilisateurDAO implements UtilisateurDAO {

    @Override
    public Optional<Utilisateur> findByEmail(String email) {

        EntityManager em = JpaUtil.createEntityManager();

        try {

            List<Utilisateur> users = em.createQuery(
                    """
                    SELECT u
                    FROM Utilisateur u
                    WHERE u.email = :email
                    """,
                    Utilisateur.class
            )
            .setParameter("email", email)
            .getResultList();

            return users.stream().findFirst();

        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Utilisateur> findById(Long id) {

        EntityManager em = JpaUtil.createEntityManager();

        try {

            return Optional.ofNullable(
                    em.find(Utilisateur.class, id)
            );

        } finally {
            em.close();
        }
    }
}