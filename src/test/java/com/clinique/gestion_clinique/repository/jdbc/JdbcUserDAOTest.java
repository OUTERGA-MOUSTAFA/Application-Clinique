package com.clinique.gestion_clinique.repository.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

class JdbcUserDAOTest {

    private DataSource dataSource;
    private JdbcUserDAO userDAO;

    @BeforeEach
    void setUp() throws SQLException {
        JdbcDataSource h2DataSource = new JdbcDataSource();
        h2DataSource.setURL("jdbc:h2:mem:users_" + UUID.randomUUID()
                + ";MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE");
        dataSource = h2DataSource;
        createUserTable();
        userDAO = new JdbcUserDAO(dataSource);
    }

    @Test
    void findByIdReturnsEmptyWhenUserDoesNotExist() {
        assertTrue(userDAO.findById(999L).isEmpty());
    }

    @Test
    void findByIdReturnsMatchingUser() throws SQLException {
        long id = insertUser("infirmier@example.test", BCrypt.hashpw("secret", BCrypt.gensalt()));

        assertEquals("infirmier@example.test", userDAO.findById(id).orElseThrow().getEmail());
    }

    @Test
    void findByEmailReturnsStoredBcryptHashUnchanged() throws SQLException {
        String passwordHash = BCrypt.hashpw("secret", BCrypt.gensalt());
        insertUser("medecin@example.test", passwordHash);

        assertEquals(passwordHash, userDAO.findByEmail("medecin@example.test").orElseThrow().getMotDePasse());
    }

    private void createUserTable() throws SQLException {
        String sql = "CREATE TABLE utilisateur ("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY, nom VARCHAR(100), prenom VARCHAR(100), "
                + "email VARCHAR(200) NOT NULL, mot_de_passe VARCHAR(100) NOT NULL, role VARCHAR(40) NOT NULL)";
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private long insertUser(String email, String passwordHash) throws SQLException {
        String sql = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, "Dupont");
            statement.setString(2, "Alex");
            statement.setString(3, email);
            statement.setString(4, passwordHash);
            statement.setString(5, "INFIRMIER");
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new SQLException("Aucun identifiant utilisateur généré");
                }
                return generatedKeys.getLong(1);
            }
        }
    }
}