package com.clinique.gestion_clinique.repository.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.clinique.gestion_clinique.entity.Patient;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JdbcPatientDAOTest {

    private JdbcPatientDAO patientDAO;

    @BeforeEach
    void setUp() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:patients_" + UUID.randomUUID()
                + ";MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE");
        createPatientTable(dataSource);
        patientDAO = new JdbcPatientDAO(dataSource);
    }

    @Test
    void findByIdReturnsEmptyWhenPatientDoesNotExist() {
        assertTrue(patientDAO.findById(999L).isEmpty());
    }

    @Test
    void saveReturnsPatientWithGeneratedId() {
        Patient savedPatient = patientDAO.save(createPatient(null));

        assertNotNull(savedPatient.getId());
        assertNotNull(savedPatient.getHeureArrivee());
        assertEquals(savedPatient.getId(), patientDAO.findById(savedPatient.getId()).orElseThrow().getId());
    }

    @Test
    void findByDateReturnsOnlyPatientsArrivedOnThatDate() {
        Patient expected = patientDAO.save(createPatient(LocalDateTime.of(2026, 9, 30, 9, 15)));
        patientDAO.save(createPatient(LocalDateTime.of(2026, 9, 29, 16, 45)));

        List<Patient> patients = patientDAO.findByDate(LocalDate.of(2026, 9, 30));

        assertEquals(1, patients.size());
        assertEquals(expected.getId(), patients.get(0).getId());
    }

    private void createPatientTable(DataSource dataSource) throws SQLException {
        String sql = "CREATE TABLE patient ("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                + "nom VARCHAR(100), prenom VARCHAR(100), date_naissance DATE, num_secu VARCHAR(50), "
                + "tension VARCHAR(30), frequence_cardiaque INT, temperature DECIMAL(5, 2), "
                + "frequence_respiratoire INT, heure_arrivee TIMESTAMP, statut VARCHAR(30))";
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private Patient createPatient(LocalDateTime heureArrivee) {
        Patient patient = new Patient();
        patient.setNom("Martin");
        patient.setPrenom("Lea");
        patient.setDateNaissance(LocalDate.of(1990, 4, 12));
        patient.setNumSecu("1234567890123");
        patient.setTension("120/80");
        patient.setFrequenceCardiaque(72);
        patient.setTemperature(36.60);
        patient.setFrequenceRespiratoire(16);
        patient.setHeureArrivee(heureArrivee);
        patient.setStatut("EN_ATTENTE");
        return patient;
    }
}