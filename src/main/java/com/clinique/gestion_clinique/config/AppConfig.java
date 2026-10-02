package com.clinique.gestion_clinique.config;

import com.clinique.gestion_clinique.repository.ConsultationDAO;
import com.clinique.gestion_clinique.repository.PatientDAO;
import com.clinique.gestion_clinique.repository.jdbc.JdbcConsultationDAO;
import com.clinique.gestion_clinique.repository.jdbc.JdbcPatientDAO;

import javax.sql.DataSource;

public final class AppConfig {

    private AppConfig() {
    }

    private static DataSource dataSource() {

        return ConnectionManager.getDataSource();
    }

    public static PatientDAO patientDAO() {

        return new JdbcPatientDAO(
                dataSource()
        );
    }

    public static ConsultationDAO consultationDAO() {

        return new JdbcConsultationDAO(
                dataSource()
        );
    }
}