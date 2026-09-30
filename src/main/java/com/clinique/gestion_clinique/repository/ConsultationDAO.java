package com.clinique.gestion_clinique.repository;

import com.clinique.gestion_clinique.model.Consultation;
import java.util.List;
import java.util.Optional;

/** Contrat d'accès aux données des consultations. */
public interface ConsultationDAO {

    /**
     * Enregistre une consultation.
     *
     * @param c consultation à enregistrer
     * @return la consultation enregistrée, notamment avec son identifiant généré
     */
    Consultation save(Consultation c);

    /**
     * Recherche une consultation à partir de l'identifiant de son patient.
     *
     * @param patientId identifiant du patient concerné
     * @return la consultation associée, ou une valeur vide si aucune n'existe
     */
    Optional<Consultation> findByPatient(Long patientId);

    /**
     * Récupère toutes les consultations.
     *
     * @return la liste des consultations, éventuellement vide
     */
    List<Consultation> findAll();
}