package com.clinique.gestion_clinique.repository.jdbc;

import com.clinique.gestion_clinique.model.Consultation;
import com.clinique.gestion_clinique.model.Patient;
import com.clinique.gestion_clinique.model.Statut;
import com.clinique.gestion_clinique.model.Utilisateur;
import com.clinique.gestion_clinique.repository.ConsultationDAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implémentation JDBC de l'accès aux données des consultations.
 * Les relations vers le patient et le médecin sont représentées par leurs identifiants lors du mapping.
 */
public class JdbcConsultationDAO implements ConsultationDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcConsultationDAO.class);

    private static final String INSERT_SQL = "INSERT INTO consultation "
            + "(patient_id, medecin_id, motif, observations, diagnostic, traitement, cout, statut, date_consultation) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_PATIENT_SQL = "SELECT * FROM consultation WHERE patient_id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM consultation ORDER BY date_consultation DESC";

    private final DataSource dataSource;

    /**
     * Crée un DAO de consultations utilisant la source de données fournie.
     *
     * @param dataSource source de connexions JDBC
     */
    public JdbcConsultationDAO(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource, "dataSource ne doit pas être null");
    }

    /**
     * Enregistre une consultation et récupère l'identifiant généré.
     *
     * @param consultation consultation à enregistrer
     * @return la consultation avec son identifiant généré
     */
    @Override
    public Consultation save(Consultation consultation) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, consultation.getPatient().getId());
            statement.setLong(2, consultation.getMedecin().getId());
            statement.setString(3, consultation.getMotif());
            statement.setString(4, consultation.getObservations());
            statement.setString(5, consultation.getDiagnostic());
            statement.setString(6, consultation.getTraitement());
            statement.setBigDecimal(7, consultation.getCout());
            statement.setString(8, consultation.getStatut().name());
            LocalDateTime dateConsultation = consultation.getDateConsultation();
            statement.setTimestamp(9, dateConsultation == null ? null : Timestamp.valueOf(dateConsultation));

            if (statement.executeUpdate() == 0) {
                throw new SQLException("L'insertion de la consultation a échoué");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new SQLException("Aucun identifiant généré pour la consultation");
                }
                consultation.setId(generatedKeys.getLong(1));
            }
            return consultation;
        } catch (SQLException exception) {
            LOGGER.error("Échec de l'enregistrement de la consultation", exception);
            throw new RuntimeException("Erreur lors de l'enregistrement de la consultation", exception);
        }
    }

    /**
     * Recherche une consultation par identifiant de patient.
     *
     * @param patientId identifiant du patient
     * @return la consultation trouvée, ou une valeur vide si aucune n'existe
     */
    @Override
    public Optional<Consultation> findByPatient(Long patientId) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_PATIENT_SQL)) {
            statement.setLong(1, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(mapRow(resultSet)) : Optional.empty();
            }
        } catch (SQLException exception) {
            LOGGER.error("Échec de la recherche de la consultation par patient", exception);
            throw new RuntimeException("Erreur lors de la recherche de la consultation par patient", exception);
        }
    }

    /**
     * Récupère les consultations de la plus récente à la plus ancienne.
     *
     * @return la liste des consultations, éventuellement vide
     */
    @Override
    public List<Consultation> findAll() {
        List<Consultation> consultations = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                consultations.add(mapRow(resultSet));
            }
            return consultations;
        } catch (SQLException exception) {
            LOGGER.error("Échec de la récupération des consultations", exception);
            throw new RuntimeException("Erreur lors de la récupération des consultations", exception);
        }
    }

    /**
     * Convertit la ligne courante du résultat SQL en entité consultation, y compris ses relations par ID.
     * Le statut stocké en base est converti en valeur de l'enum {@link Statut}.
     *
     * @param resultSet résultat positionné sur la ligne à mapper
     * @return la consultation construite à partir des colonnes de la ligne
     * @throws SQLException si la lecture d'une colonne ou la conversion du statut échoue
     */
    private Consultation mapRow(ResultSet resultSet) throws SQLException {
        Consultation consultation = new Consultation();
        consultation.setId(resultSet.getLong("id"));

        Patient patient = new Patient();
        patient.setId(resultSet.getLong("patient_id"));
        consultation.setPatient(patient);

        Utilisateur medecin = new Utilisateur();
        medecin.setId(resultSet.getLong("medecin_id"));
        consultation.setMedecin(medecin);

        consultation.setMotif(resultSet.getString("motif"));
        consultation.setObservations(resultSet.getString("observations"));
        consultation.setDiagnostic(resultSet.getString("diagnostic"));
        consultation.setTraitement(resultSet.getString("traitement"));
        BigDecimal cout = resultSet.getBigDecimal("cout");
        consultation.setCout(cout);

        String statutValue = resultSet.getString("statut");
        if (statutValue != null) {
            try {
                consultation.setStatut(Statut.valueOf(statutValue.toUpperCase(Locale.ROOT)));
            } catch (IllegalArgumentException exception) {
                throw new SQLException("Statut de consultation inconnu : " + statutValue, exception);
            }
        }

        Timestamp dateConsultation = resultSet.getTimestamp("date_consultation");
        consultation.setDateConsultation(dateConsultation == null ? null : dateConsultation.toLocalDateTime());
        return consultation;
    }
}