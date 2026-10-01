package com.clinique.gestion_clinique.config;

import com.clinique.gestion_clinique.repository.PatientDAO;
import com.clinique.gestion_clinique.repository.jdbc.JdbcPatientDAO;

public final class AppConfig {

    // pour donnéer PatientService un PatientDAO
    private AppConfig() {
    }

    public static PatientDAO patientDAO() {
        return new JdbcPatientDAO(
                ConnectionManager.getDataSource()
        );
    }
}