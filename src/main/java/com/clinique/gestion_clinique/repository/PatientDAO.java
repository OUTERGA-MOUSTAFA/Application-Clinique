package com.clinique.gestion_clinique.repository;

import com.clinique.gestion_clinique.entity.Patient;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/** Contrat d'accès aux données des patients. */
public interface PatientDAO {

    /**
     * Recherche un patient à partir de son identifiant.
     *
     * @param id identifiant du patient recherché
     * @return le patient correspondant, ou une valeur vide s'il n'existe pas
     */
    Optional<Patient> findById(Long id);

    /**
     * Récupère tous les patients.
     *
     * @return la liste des patients, éventuellement vide
     */
    List<Patient> findAll();

    /**
     * Recherche les patients associés à une date donnée.
     *
     * @param date date de recherche
     * @return la liste des patients trouvés, éventuellement vide
     */
    List<Patient> findByDate(LocalDate date);

    /**
     * Enregistre un patient.
     *
     * @param p patient à enregistrer
     * @return le patient enregistré, notamment avec son identifiant généré
     */
    Patient save(Patient p);

    /**
     * Met à jour les données d'un patient existant.
     *
     * @param p patient contenant les données à mettre à jour
     */
    void update(Patient p);
}