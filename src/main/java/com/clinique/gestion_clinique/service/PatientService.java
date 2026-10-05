package com.clinique.gestion_clinique.service;

import com.clinique.gestion_clinique.entity.Patient;
import com.clinique.gestion_clinique.repository.PatientDAO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

public class PatientService {

    private final PatientDAO patientDAO;

    public PatientService(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public Patient enregistrer(
            String nom,
            String prenom,
            LocalDate dateNaissance,
            String numSecu,
            String tension,
            int frequenceCardiaque,
            double temperature,
            int frequenceRespiratoire) {

        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom est obligatoire.");
        }

        if (prenom == null || prenom.isBlank()) {
            throw new IllegalArgumentException("Le prénom est obligatoire.");
        }

        if (dateNaissance == null) {
            throw new IllegalArgumentException(
                    "La date de naissance est obligatoire.");
        }

        if (numSecu == null || numSecu.isBlank()) {
            throw new IllegalArgumentException(
                    "Le numéro de sécurité sociale est obligatoire.");
        }

        if (tension == null || tension.isBlank()) {
            throw new IllegalArgumentException(
                    "La tension est obligatoire.");
        }

        if (frequenceCardiaque <= 0) {
            throw new IllegalArgumentException(
                    "La fréquence cardiaque doit être positive.");
        }

        if (temperature <= 0) {
            throw new IllegalArgumentException(
                    "La température doit être positive.");
        }

        if (frequenceRespiratoire <= 0) {
            throw new IllegalArgumentException(
                    "La fréquence respiratoire doit être positive.");
        }

        Patient patient = new Patient();

        patient.setNom(nom.trim());
        patient.setPrenom(prenom.trim());
        patient.setDateNaissance(dateNaissance);
        patient.setNumSecu(numSecu.trim());
        patient.setTension(tension.trim());
        patient.setFrequenceCardiaque(frequenceCardiaque);
        patient.setTemperature(temperature);
        patient.setFrequenceRespiratoire(frequenceRespiratoire);

        /**
         * L'heure d'arrivée est générée par le serveur.
         * Le client ne peut pas la choisir.
         */
        patient.setHeureArrivee(LocalDateTime.now());

        /**
         * Après l'enregistrement, le patient est en attente
         * d'une consultation.
         */
        patient.setStatut("EN_ATTENTE");

        return patientDAO.save(patient);
    }

    public Optional<Patient> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        return patientDAO.findById(id);
    }

    // public List<Patient> patientsDuJour() {

    // LocalDate today = LocalDate.now();

    // return patientDAO.findAll()
    // .stream()
    // .filter(patient -> patient.getHeureArrivee() != null
    // && patient.getHeureArrivee()
    // .toLocalDate()
    // .equals(today))
    // .sorted((p1, p2) -> p1.getHeureArrivee()
    // .compareTo(p2.getHeureArrivee()))
    // .toList();
    // }

    public void changerStatut(Long patientId, String statut) {// pour généraliste

        Patient patient = patientDAO.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Patient introuvable."));

        patient.setStatut(statut);

        patientDAO.update(patient);
    }

    public void update(Patient patient) {

        if (patient == null ||
                patient.getId() == null) {

            throw new IllegalArgumentException(
                    "Patient invalide.");
        }

        patientDAO.update(patient);
    }

    public List<Patient> filtrerParPeriode(String periode) {

        LocalDate aujourdHui = LocalDate.now();

        LocalDate debut;
        LocalDate fin;

        switch (periode) {

            case "aujourd-hui":

                debut = aujourdHui;
                fin = aujourdHui;

                break;

            case "hier":

                debut = aujourdHui.minusDays(1);
                fin = aujourdHui.minusDays(1);

                break;

            case "semaine":

                debut = aujourdHui.with(
                        TemporalAdjusters.previousOrSame(
                                DayOfWeek.MONDAY));

                fin = debut.plusDays(6);

                break;

            case "mois":

                debut = aujourdHui.withDayOfMonth(1);

                fin = aujourdHui.withDayOfMonth(
                        aujourdHui.lengthOfMonth());

                break;

            default:

                // Par défaut : aujourd'hui
                debut = aujourdHui;
                fin = aujourdHui;
        }

        return patientDAO.findAll()
                .stream()
                .filter(patient -> patient.getHeureArrivee() != null)
                .filter(patient -> {

                    LocalDate dateArrivee = patient.getHeureArrivee()
                            .toLocalDate();

                    return !dateArrivee.isBefore(debut)
                            && !dateArrivee.isAfter(fin);
                })
                .sorted((p1, p2) -> p1.getHeureArrivee()
                        .compareTo(
                                p2.getHeureArrivee()))
                .toList();
    }
}