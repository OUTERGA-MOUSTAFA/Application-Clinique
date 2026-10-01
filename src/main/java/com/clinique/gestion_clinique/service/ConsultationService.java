package com.clinique.gestion_clinique.service;

import com.clinique.gestion_clinique.entity.Consultation;
import com.clinique.gestion_clinique.entity.Patient;
import com.clinique.gestion_clinique.entity.Statut;
import com.clinique.gestion_clinique.entity.Utilisateur;
import com.clinique.gestion_clinique.repository.ConsultationDAO;
import com.clinique.gestion_clinique.repository.PatientDAO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class ConsultationService {

    private static final BigDecimal COUT_FIXE = new BigDecimal("150.00");

    private final ConsultationDAO consultationDAO;
    private final PatientDAO patientDAO;

    public ConsultationService(
            ConsultationDAO consultationDAO,
            PatientDAO patientDAO) {
        this.consultationDAO = consultationDAO;
        this.patientDAO = patientDAO;
    }

    public Consultation cloturer(
            Long patientId,
            Utilisateur medecin,
            String motif,
            String observations,
            String diagnostic,
            String traitement) {

        // 1. Vérifier le patient
        Optional<Patient> patientOptional = patientDAO.findById(patientId);

        if (patientOptional.isEmpty()) {

            throw new IllegalArgumentException(
                    "Patient introuvable.");
        }

        Patient patient = patientOptional.get();

        // 2. Vérifier que le patient n'a pas déjà
        // une consultation

        if (consultationDAO
                .findByPatient(patientId)
                .isPresent()) {

            throw new IllegalStateException(
                    "Ce patient a déjà été consulté.");
        }

        // 3. Validation métier

        if (motif == null || motif.isBlank()) {

            throw new IllegalArgumentException(
                    "Le motif est obligatoire.");
        }

        if (diagnostic == null ||
                diagnostic.isBlank()) {

            throw new IllegalArgumentException(
                    "Le diagnostic est obligatoire.");
        }

        if (traitement == null ||
                traitement.isBlank()) {

            throw new IllegalArgumentException(
                    "Le traitement est obligatoire.");
        }

        // 4. Vérifier le médecin

        if (medecin == null ||
                medecin.getId() == null) {

            throw new IllegalStateException(
                    "Médecin non authentifié.");
        }

        // 5. Création côté SERVEUR

        Consultation consultation = new Consultation();

        consultation.setPatient(patient);

        // Médecin venant de la SESSION
        consultation.setMedecin(medecin);

        consultation.setMotif(motif);

        consultation.setObservations(
                observations);

        consultation.setDiagnostic(
                diagnostic);

        consultation.setTraitement(
                traitement);

        // Règle métier serveur
        consultation.setCout(
                COUT_FIXE);

        // Règle métier serveur
        consultation.setStatut(
                Statut.TERMINEE);

        // Règle métier serveur
        consultation.setDateConsultation(
                LocalDateTime.now());

        // 6. Sauvegarde DB

        return consultationDAO.save(
                consultation);
    }
}