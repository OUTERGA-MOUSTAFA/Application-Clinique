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

        /**
         * Retourne les patients qui sont encore en attente
         * d'une consultation.
         */
        public List<Patient> patientsEnAttente() {

                return patientDAO.findAll()
                                .stream()

                                // Patient valide
                                .filter(patient -> patient.getId() != null)

                                // Seulement les patients en attente
                                .filter(patient -> "EN_ATTENTE".equals(
                                                patient.getStatut()))

                                // Plus ancien patient en premier
                                .sorted((p1, p2) -> p1.getHeureArrivee()
                                                .compareTo(
                                                                p2.getHeureArrivee()))

                                .toList();
        }

        /**
         * Commence la prise en charge d'un patient.
         *
         * EN_ATTENTE -> EN_COURS
         */
        public Patient commencerConsultation(Long patientId) {

                Patient patient = patientDAO.findById(patientId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Patient introuvable."));

                // Le patient doit être en attente
                if (!"EN_ATTENTE".equals(patient.getStatut())) {

                        throw new IllegalStateException(
                                        "Ce patient n'est pas en attente.");
                }

                // Le patient passe en cours
                patient.setStatut("EN_COURS");

                // Sauvegarde dans MySQL
                patientDAO.update(patient);

                return patient;
        }

        /**
         * Clôture la consultation.
         *
         * EN_COURS -> TERMINEE
         */
        public Consultation cloturer(
                        Long patientId,
                        Utilisateur medecin,
                        String motif,
                        String observations,
                        String diagnostic,
                        String traitement) {

                // ==========================================
                // 1. Vérifier que le patient existe
                // ==========================================

                Patient patient = patientDAO.findById(patientId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Patient introuvable."));

                // ==========================================
                // 2. Vérifier que le médecin est connecté
                // ==========================================

                if (medecin == null ||
                                medecin.getId() == null) {

                        throw new IllegalStateException(
                                        "Médecin non authentifié.");
                }

                // ==========================================
                // 3. Vérifier si le patient a déjà
                // une consultation
                // ==========================================

                if (consultationDAO
                                .findByPatient(patientId)
                                .isPresent()) {

                        throw new IllegalStateException(
                                        "Ce patient a déjà été consulté.");
                }

                // ==========================================
                // 4. Vérifier le statut du patient
                // ==========================================

                if (!"EN_COURS".equals(patient.getStatut())) {

                        throw new IllegalStateException(
                                        "La consultation doit être en cours.");
                }

                // ==========================================
                // 5. Validation du motif
                // ==========================================

                if (motif == null ||
                                motif.isBlank()) {

                        throw new IllegalArgumentException(
                                        "Le motif est obligatoire.");
                }

                // ==========================================
                // 6. Validation du diagnostic
                // ==========================================

                if (diagnostic == null ||
                                diagnostic.isBlank()) {

                        throw new IllegalArgumentException(
                                        "Le diagnostic est obligatoire.");
                }

                // ==========================================
                // 7. Validation du traitement
                // ==========================================

                if (traitement == null ||
                                traitement.isBlank()) {

                        throw new IllegalArgumentException(
                                        "Le traitement est obligatoire.");
                }

                // ==========================================
                // 8. Construire la consultation
                // côté serveur
                // ==========================================

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

                // Le prix est décidé par le serveur
                consultation.setCout(
                                COUT_FIXE);

                // La consultation est terminée
                consultation.setStatut(
                                Statut.TERMINEE);

                consultation.setDateConsultation(
                                LocalDateTime.now());

                // ==========================================
                // 9. Mettre le PATIENT à TERMINEE
                // ==========================================

                patient.setStatut("TERMINEE");

                patientDAO.update(patient);

                // ==========================================
                // 10. Enregistrer la consultation
                // ==========================================

                return consultationDAO.save(
                                consultation);
        }

        public List<Patient> patientsParStatut(String statut) {
                return patientDAO.findByStatut(statut);
        }
}