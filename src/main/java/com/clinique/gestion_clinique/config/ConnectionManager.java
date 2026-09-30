package com.clinique.gestion_clinique.config;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/** Fournit des connexions à partir de la ressource JNDI configurée dans Tomcat. */
public final class ConnectionManager {

    private static final String JNDI_NAME = "java:comp/env/jdbc/cliniqueDB";

    private ConnectionManager() {
    }

    public static DataSource getDataSource() {
        return DataSourceHolder.DATA_SOURCE;
    }

    /**
     * Obtient une connexion depuis le DataSource JNDI.
     *
     * @return une connexion à la base de données
     * @throws SQLException si une erreur survient lors de l'obtention de la connexion
     * @throws IllegalStateException si le DataSource JNDI est introuvable ou invalide
     */
    public static Connection getConnection() throws SQLException {
        return DataSourceHolder.DATA_SOURCE.getConnection();
    }

    private static DataSource lookupDataSource() {
        try {
            InitialContext context = new InitialContext();
            try {
                return (DataSource) context.lookup(JNDI_NAME);
            } finally {
                context.close();
            }
        } catch (NamingException exception) {
            throw new IllegalStateException("Impossible de récupérer le DataSource JNDI " + JNDI_NAME, exception);
        }
    }

    private static final class DataSourceHolder {
        private static final DataSource DATA_SOURCE = lookupDataSource();
    }
}