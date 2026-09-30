package com.clinique.gestion_clinique.repository;

import com.clinique.gestion_clinique.entity.Utilisateur;
import java.util.Optional;

/** Contrat d'accès aux données des utilisateurs. */
public interface UtilisateurDAO {

    /**
     * Recherche un utilisateur à partir de son adresse e-mail.
     *
     * @param email adresse e-mail recherchée
     * @return l'utilisateur correspondant, ou une valeur vide s'il n'existe pas
     */
    Optional<Utilisateur> findByEmail(String email);

    /**
     * Recherche un utilisateur à partir de son identifiant.
     *
     * @param id identifiant de l'utilisateur recherché
     * @return l'utilisateur correspondant, ou une valeur vide s'il n'existe pas
     */
    Optional<Utilisateur> findById(Long id);
}