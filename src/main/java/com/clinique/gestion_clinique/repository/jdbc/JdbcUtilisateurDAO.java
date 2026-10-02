package com.clinique.gestion_clinique.repository.jdbc;

import com.clinique.gestion_clinique.entity.Role;
import com.clinique.gestion_clinique.entity.Utilisateur;
import com.clinique.gestion_clinique.repository.UtilisateurDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Implémentation JDBC de l'accès aux données des utilisateurs. */
public class JdbcUtilisateurDAO implements UtilisateurDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcUtilisateurDAO.class);

    private static final String FIND_BY_EMAIL_SQL = "SELECT id, nom, email, mot_de_passe, role "
            + "FROM utilisateur WHERE email = ?";
    private static final String FIND_BY_ID_SQL = "SELECT id, nom, email, mot_de_passe, role "
            + "FROM utilisateur WHERE id = ?";

    private final DataSource dataSource;

    public JdbcUtilisateurDAO(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource, "dataSource ne doit pas être null");
    }

    @Override
    public Optional<Utilisateur> findByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL_SQL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(mapRow(resultSet)) : Optional.empty();
            }
        } catch (SQLException exception) {
            LOGGER.error("Échec de la recherche de l'utilisateur par e-mail", exception);
            throw new RuntimeException("Erreur lors de la recherche de l'utilisateur par e-mail", exception);
        }
    }

    @Override
    public Optional<Utilisateur> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(mapRow(resultSet)) : Optional.empty();
            }
        } catch (SQLException exception) {
            LOGGER.error("Échec de la recherche de l'utilisateur par identifiant", exception);
            throw new RuntimeException("Erreur lors de la recherche de l'utilisateur par identifiant", exception);
        }
    }

    private Utilisateur mapRow(ResultSet resultSet) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(resultSet.getLong("id"));
        utilisateur.setNom(resultSet.getString("nom"));
        utilisateur.setPrenom(resultSet.getString("prenom"));
        utilisateur.setEmail(resultSet.getString("email"));
        utilisateur.setMotDePasse(resultSet.getString("mot_de_passe"));
        utilisateur.setRole(Role.valueOf(resultSet.getString("role")));
        return utilisateur;
    }
}