package com.clinique.gestion_clinique.service;

import com.clinique.gestion_clinique.entity.Patient;
import com.clinique.gestion_clinique.repository.PatientDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PatientService {

    private final PatientDAO patientDAO;

    public PatientService(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public Patient createPatient(Patient patient) {

        if (patient.getNom() == null ||
            patient.getNom().isBlank()) {

            throw new IllegalArgumentException(
                    "Le nom est obligatoire"
            );
        }

        if (patient.getPrenom() == null ||
            patient.getPrenom().isBlank()) {

            throw new IllegalArgumentException(
                    "Le prénom est obligatoire"
            );
        }

        patient.setHeureArrivee(
                java.time.LocalDateTime.now()
        );

        patient.setStatut("EN_ATTENTE");

        return patientDAO.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientDAO.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientDAO.findById(id);
    }

    public List<Patient> getPatientsByDate(LocalDate date) {
        return patientDAO.findByDate(date);
    }

    public void updatePatient(Patient patient) {
        patientDAO.update(patient);
    }
}