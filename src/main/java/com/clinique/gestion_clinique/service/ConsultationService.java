package com.clinique.gestion_clinique.service;

import com.clinique.gestion_clinique.entity.Consultation;
import com.clinique.gestion_clinique.entity.Patient;
import com.clinique.gestion_clinique.entity.Statut;
import com.clinique.gestion_clinique.entity.Utilisateur;
import com.clinique.gestion_clinique.repository.ConsultationDAO;
import com.clinique.gestion_clinique.repository.PatientDAO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    public List<Patient> patientsEnAttente() {

        return patientDAO.findAll()
                .stream()
                .filter(patient -> patient.getId() != null
                        && consultationDAO
                                .findByPatient(patient.getId())
                                .isEmpty())
                .toList();
    }

    public Consultation cloturer(
            Long patientId,
            Utilisateur medecin,
            String motif,
            String observations,
            String diagnostic,
            String traitement) {

        /**
         * 1. Patient existe ?
         */
        Patient patient = patientDAO.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Patient introuvable."));

        /**
         * 2. Patient déjà consulté ?
         */
        if (consultationDAO
                .findByPatient(patientId)
                .isPresent()) {

            throw new IllegalStateException(
                    "Ce patient a déjà été consulté.");
        }

        /**
         * 3. Validation métier.
         */
        if (motif == null ||
                motif.isBlank()) {

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

        /**
         * 4. Médecin connecté.
         */
        if (medecin == null ||
                medecin.getId() == null) {

            throw new IllegalStateException(
                    "Médecin non authentifié.");
        }

        /**
         * 5. Construction côté serveur.
         *
         * Le client ne peut PAS choisir :
         * - médecin
         * - coût
         * - statut
         * - date
         */
        Consultation consultation = new Consultation();

        consultation.setPatient(patient);

        consultation.setMedecin(medecin);

        consultation.setMotif(
                motif.trim());

        consultation.setObservations(
                observations == null
                        ? null
                        : observations.trim());

        consultation.setDiagnostic(
                diagnostic.trim());

        consultation.setTraitement(
                traitement.trim());

        consultation.setCout(
                COUT_FIXE);

        consultation.setStatut(
                Statut.TERMINEE);

        consultation.setDateConsultation(
                LocalDateTime.now());

        /**
         * 6. Persistence.
         */
        return consultationDAO.save(
                consultation);
    }
}