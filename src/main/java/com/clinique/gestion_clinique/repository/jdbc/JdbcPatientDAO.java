package com.clinique.gestion_clinique.repository.jdbc;

import com.clinique.gestion_clinique.entity.Patient;
import com.clinique.gestion_clinique.repository.PatientDAO;

import javax.sql.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcPatientDAO implements PatientDAO {

	private final DataSource dataSource;

	public JdbcPatientDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Optional<Patient> findById(Long id) {

		String sql = """
				SELECT
				    id,
				    nom,
				    prenom,
				    date_naissance,
				    num_secu,
				    tension,
				    frequence_cardiaque,
				    temperature,
				    frequence_respiratoire,
				    heure_arrivee,
				    statut
				FROM patient
				WHERE id = ?
				""";

		try (
				Connection connection = dataSource.getConnection();

				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setLong(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {

				if (resultSet.next()) {

					return Optional.of(
							mapRow(resultSet));
				}

			}

		} catch (SQLException e) {

			throw new RuntimeException(
					"Erreur lors de la recherche du patient.",
					e);
		}

		return Optional.empty();
	}

	@Override
	public List<Patient> findAll() {

		String sql = """
				SELECT
				    id,
				    nom,
				    prenom,
				    date_naissance,
				    num_secu,
				    tension,
				    frequence_cardiaque,
				    temperature,
				    frequence_respiratoire,
				    heure_arrivee,
				    statut
				FROM patient
				ORDER BY heure_arrivee ASC
				""";

		List<Patient> patients = new ArrayList<>();

		try (
				Connection connection = dataSource.getConnection();

				PreparedStatement statement = connection.prepareStatement(sql);

				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {

				patients.add(
						mapRow(resultSet));
			}

		} catch (SQLException e) {

			throw new RuntimeException(
					"Erreur lors de la récupération des patients.",
					e);
		}

		return patients;
	}

	@Override
	public List<Patient> findByDate(LocalDate date) {

		String sql = """
				SELECT
				    id,
				    nom,
				    prenom,
				    date_naissance,
				    num_secu,
				    tension,
				    frequence_cardiaque,
				    temperature,
				    frequence_respiratoire,
				    heure_arrivee,
				    statut
				FROM patient
				WHERE DATE(heure_arrivee) = ?
				ORDER BY heure_arrivee ASC
				""";

		List<Patient> patients = new ArrayList<>();

		try (
				Connection connection = dataSource.getConnection();

				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setDate(
					1,
					Date.valueOf(date));

			try (ResultSet resultSet = statement.executeQuery()) {

				while (resultSet.next()) {

					patients.add(
							mapRow(resultSet));
				}
			}

		} catch (SQLException e) {

			throw new RuntimeException(
					"Erreur lors de la recherche par date.",
					e);
		}

		return patients;
	}

	@Override
	public Patient save(Patient patient) {

		String sql = """
				INSERT INTO patient (
				    nom,
				    prenom,
				    date_naissance,
				    num_secu,
				    tension,
				    frequence_cardiaque,
				    temperature,
				    frequence_respiratoire,
				    heure_arrivee,
				    statut
				)
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";

		try (
				Connection connection = dataSource.getConnection();

				PreparedStatement statement = connection.prepareStatement(
						sql,
						Statement.RETURN_GENERATED_KEYS)) {

			statement.setString(
					1,
					patient.getNom());

			statement.setString(
					2,
					patient.getPrenom());

			statement.setDate(
					3,
					Date.valueOf(
							patient.getDateNaissance()));

			statement.setString(
					4,
					patient.getNumSecu());

			statement.setString(
					5,
					patient.getTension());

			statement.setInt(
					6,
					patient.getFrequenceCardiaque());

			statement.setDouble(
					7,
					patient.getTemperature());

			statement.setInt(
					8,
					patient.getFrequenceRespiratoire());

			statement.setTimestamp(
					9,
					Timestamp.valueOf(
							patient.getHeureArrivee()));

			statement.setString(
					10,
					patient.getStatut());

			statement.executeUpdate();

			try (
					ResultSet keys = statement.getGeneratedKeys()) {

				if (keys.next()) {

					patient.setId(
							keys.getLong(1));
				}
			}

			return patient;

		} catch (SQLException e) {

			throw new RuntimeException(
					"Erreur lors de l'enregistrement du patient.",
					e);
		}
	}

	@Override
	public void update(Patient patient) {

		String sql = """
				UPDATE patient
				SET nom = ?,
				    prenom = ?,
				    date_naissance = ?,
				    num_secu = ?,
				    tension = ?,
				    frequence_cardiaque = ?,
				    temperature = ?,
				    frequence_respiratoire = ?,
				    heure_arrivee = ?,
				    statut = ?
				WHERE id = ?
				""";

		try (
				Connection connection = dataSource.getConnection();

				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, patient.getNom());
			statement.setString(2, patient.getPrenom());

			statement.setDate(
					3,
					Date.valueOf(
							patient.getDateNaissance()));

			statement.setString(4, patient.getNumSecu());
			statement.setString(5, patient.getTension());
			statement.setInt(
					6,
					patient.getFrequenceCardiaque());

			statement.setDouble(
					7,
					patient.getTemperature());

			statement.setInt(
					8,
					patient.getFrequenceRespiratoire());

			statement.setTimestamp(
					9,
					Timestamp.valueOf(
							patient.getHeureArrivee()));

			statement.setString(
					10,
					patient.getStatut());

			statement.setLong(
					11,
					patient.getId());

			statement.executeUpdate();

		} catch (SQLException e) {

			throw new RuntimeException(
					"Erreur lors de la mise à jour du patient.",
					e);
		}
	}

	private Patient mapRow(ResultSet resultSet)
			throws SQLException {

		Patient patient = new Patient();

		patient.setId(
				resultSet.getLong("id"));

		patient.setNom(
				resultSet.getString("nom"));

		patient.setPrenom(
				resultSet.getString("prenom"));

		Date dateNaissance = resultSet.getDate("date_naissance");

		if (dateNaissance != null) {

			patient.setDateNaissance(
					dateNaissance.toLocalDate());
		}

		patient.setNumSecu(
				resultSet.getString("num_secu"));

		patient.setTension(
				resultSet.getString("tension"));

		patient.setFrequenceCardiaque(
				resultSet.getInt(
						"frequence_cardiaque"));

		patient.setTemperature(
				resultSet.getDouble(
						"temperature"));

		patient.setFrequenceRespiratoire(
				resultSet.getInt(
						"frequence_respiratoire"));

		Timestamp heureArrivee = resultSet.getTimestamp(
				"heure_arrivee");

		if (heureArrivee != null) {

			patient.setHeureArrivee(
					heureArrivee.toLocalDateTime());
		}

		patient.setStatut(
				resultSet.getString("statut"));

		return patient;
	}

	@Override
	public List<Patient> findByStatut(String statut) {

		String sql = """
				SELECT
				    id,
				    nom,
				    prenom,
				    date_naissance,
				    num_secu,
				    tension,
				    frequence_cardiaque,
				    temperature,
				    frequence_respiratoire,
				    heure_arrivee,
				    statut
				FROM patient
				WHERE statut = ?
				ORDER BY heure_arrivee ASC
				""";

		List<Patient> patients = new ArrayList<>();

		try (
				Connection connection = dataSource.getConnection();

				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, statut);

			try (ResultSet resultSet = statement.executeQuery()) {

				while (resultSet.next()) {

					patients.add(mapRow(resultSet));
				}
			}

		} catch (SQLException e) {

			throw new RuntimeException(
					"Erreur lors de la recherche par statut.",
					e);
		}

		return patients;
	}
}