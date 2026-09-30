package com.clinique.gestion_clinique.repository.jdbc;

import com.clinique.gestion_clinique.model.Patient;
import com.clinique.gestion_clinique.repository.PatientDAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implémentation JDBC de l'accès aux données des patients.
 * Les requêtes utilisent le {@link DataSource} injecté et ferment leurs ressources JDBC automatiquement.
 */
public class JdbcPatientDAO implements PatientDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcPatientDAO.class);

    private static final String FIND_BY_ID_SQL = "SELECT * FROM patient WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM patient ORDER BY heure_arrivee ASC";
    private static final String FIND_BY_DATE_SQL = "SELECT * FROM patient WHERE DATE(heure_arrivee) = ?";
    private static final String INSERT_SQL = "INSERT INTO patient "
            + "(nom, prenom, date_naissance, num_secu, tension, frequence_cardiaque, temperature, "
            + "frequence_respiratoire, heure_arrivee, statut) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE patient SET tension = ?, frequence_cardiaque = ?, "
            + "temperature = ?, frequence_respiratoire = ?, statut = ? WHERE id = ?";

    private final DataSource dataSource;

    /**
     * Crée un DAO de patients utilisant la source de données fournie.
     *
     * @param dataSource source de connexions JDBC
     */
    public JdbcPatientDAO(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource, "dataSource ne doit pas être null");
    }

    /**
     * Recherche un patient par son identifiant.
     *
     * @param id identifiant du patient
     * @return le patient trouvé, ou une valeur vide s'il n'existe pas
     */
    @Override
    public Optional<Patient> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(mapRow(resultSet)) : Optional.empty();
            }
        } catch (SQLException exception) {
            LOGGER.error("Échec de la recherche du patient par identifiant", exception);
            throw new RuntimeException("Erreur lors de la recherche du patient par identifiant", exception);
        }
    }

    /**
     * Récupère les patients triés par heure d'arrivée croissante.
     *
     * @return la liste des patients, éventuellement vide
     */
    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                patients.add(mapRow(resultSet));
            }
            return patients;
        } catch (SQLException exception) {
            LOGGER.error("Échec de la récupération des patients", exception);
            throw new RuntimeException("Erreur lors de la récupération des patients", exception);
        }
    }

    /**
     * Recherche les patients arrivés à la date donnée.
     *
     * @param date date d'arrivée recherchée
     * @return la liste des patients arrivés ce jour-là, éventuellement vide
     */
    @Override
    public List<Patient> findByDate(LocalDate date) {
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_DATE_SQL)) {
            statement.setDate(1, java.sql.Date.valueOf(date));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    patients.add(mapRow(resultSet));
                }
            }
            return patients;
        } catch (SQLException exception) {
            LOGGER.error("Échec de la recherche des patients par date", exception);
            throw new RuntimeException("Erreur lors de la recherche des patients par date", exception);
        }
    }

    /**
     * Enregistre un patient et récupère l'identifiant généré par la base.
     * Si aucune heure d'arrivée n'est fournie, l'heure courante est enregistrée.
     *
     * @param patient patient à enregistrer
     * @return le patient avec son identifiant et son heure d'arrivée renseignés
     */
    @Override
    public Patient save(Patient patient) {
        LocalDateTime heureArrivee = patient.getHeureArrivee() == null
            ? LocalDateTime.now()
            : patient.getHeureArrivee();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, patient.getNom());
            statement.setString(2, patient.getPrenom());
            LocalDate dateNaissance = patient.getDateNaissance();
            statement.setDate(3, dateNaissance == null ? null : java.sql.Date.valueOf(dateNaissance));
            statement.setString(4, patient.getNumSecu());
            statement.setString(5, patient.getTension());
            setNullableInteger(statement, 6, patient.getFrequenceCardiaque());
            statement.setBigDecimal(7, patient.getTemperature());
            setNullableInteger(statement, 8, patient.getFrequenceRespiratoire());
                statement.setTimestamp(9, Timestamp.valueOf(heureArrivee));
            statement.setString(10, patient.getStatut());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("L'insertion du patient a échoué");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new SQLException("Aucun identifiant généré pour le patient");
                }
                patient.setId(generatedKeys.getLong(1));
            }
            patient.setHeureArrivee(heureArrivee);
            return patient;
        } catch (SQLException exception) {
            LOGGER.error("Échec de l'enregistrement du patient", exception);
            throw new RuntimeException("Erreur lors de l'enregistrement du patient", exception);
        }
    }

    /**
     * Met à jour les signes vitaux et le statut d'un patient existant.
     *
     * @param patient patient portant l'identifiant et les nouvelles valeurs
     */
    @Override
    public void update(Patient patient) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, patient.getTension());
            setNullableInteger(statement, 2, patient.getFrequenceCardiaque());
            statement.setBigDecimal(3, patient.getTemperature());
            setNullableInteger(statement, 4, patient.getFrequenceRespiratoire());
            statement.setString(5, patient.getStatut());
            statement.setLong(6, patient.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            LOGGER.error("Échec de la mise à jour du patient", exception);
            throw new RuntimeException("Erreur lors de la mise à jour du patient", exception);
        }
    }

    /**
     * Convertit la ligne courante du résultat SQL en entité patient.
     *
     * @param resultSet résultat positionné sur la ligne à mapper
     * @return le patient construit à partir des colonnes de la ligne
     * @throws SQLException si la lecture d'une colonne échoue
     */
    private Patient mapRow(ResultSet resultSet) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setNom(resultSet.getString("nom"));
        patient.setPrenom(resultSet.getString("prenom"));

        java.sql.Date dateNaissance = resultSet.getDate("date_naissance");
        patient.setDateNaissance(dateNaissance == null ? null : dateNaissance.toLocalDate());
        patient.setNumSecu(resultSet.getString("num_secu"));
        patient.setTension(resultSet.getString("tension"));

        int frequenceCardiaque = resultSet.getInt("frequence_cardiaque");
        patient.setFrequenceCardiaque(resultSet.wasNull() ? null : frequenceCardiaque);
        BigDecimal temperature = resultSet.getBigDecimal("temperature");
        patient.setTemperature(temperature);
        int frequenceRespiratoire = resultSet.getInt("frequence_respiratoire");
        patient.setFrequenceRespiratoire(resultSet.wasNull() ? null : frequenceRespiratoire);

        Timestamp heureArrivee = resultSet.getTimestamp("heure_arrivee");
        patient.setHeureArrivee(heureArrivee == null ? null : heureArrivee.toLocalDateTime());
        patient.setStatut(resultSet.getString("statut"));
        return patient;
    }

    /**
     * Affecte un entier nullable à un paramètre JDBC.
     *
     * @param statement instruction préparée à compléter
     * @param index index du paramètre, à partir de 1
     * @param value valeur entière ou {@code null}
     * @throws SQLException si l'affectation échoue
     */
    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}