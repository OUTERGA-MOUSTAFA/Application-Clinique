package com.clinique.gestion_clinique.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Patient {

	private Long id;
	private String nom;
	private String prenom;
	private LocalDate dateNaissance;
	private String numSecu;
	private String tension;
	private int frequenceCardiaque;
	private double temperature;
	private int frequenceRespiratoire;
	private LocalDateTime heureArrivee;
	private String statut;

	public Patient() {
	}

	public Patient(Long id, String nom, String prenom, LocalDate dateNaissance, String numSecu,
			String tension, int frequenceCardiaque, double temperature, int frequenceRespiratoire,
			LocalDateTime heureArrivee, String statut) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.numSecu = numSecu;
		this.tension = tension;
		this.frequenceCardiaque = frequenceCardiaque;
		this.temperature = temperature;
		this.frequenceRespiratoire = frequenceRespiratoire;
		this.heureArrivee = heureArrivee;
		this.statut = statut;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getNumSecu() {
		return numSecu;
	}

	public void setNumSecu(String numSecu) {
		this.numSecu = numSecu;
	}

	public String getTension() {
		return tension;
	}

	public void setTension(String tension) {
		this.tension = tension;
	}

	public int getFrequenceCardiaque() {
		return frequenceCardiaque;
	}

	public void setFrequenceCardiaque(int frequenceCardiaque) {
		this.frequenceCardiaque = frequenceCardiaque;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public int getFrequenceRespiratoire() {
		return frequenceRespiratoire;
	}

	public void setFrequenceRespiratoire(int frequenceRespiratoire) {
		this.frequenceRespiratoire = frequenceRespiratoire;
	}

	public LocalDateTime getHeureArrivee() {
		return heureArrivee;
	}

	public void setHeureArrivee(LocalDateTime heureArrivee) {
		this.heureArrivee = heureArrivee;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	@Override
	public String toString() {
		return "Patient{" +
				"id=" + id +
				", nom='" + nom + '\'' +
				", prenom='" + prenom + '\'' +
				", dateNaissance=" + dateNaissance +
				", numSecu='" + numSecu + '\'' +
				", tension='" + tension + '\'' +
				", frequenceCardiaque=" + frequenceCardiaque +
				", temperature=" + temperature +
				", frequenceRespiratoire=" + frequenceRespiratoire +
				", heureArrivee=" + heureArrivee +
				", statut='" + statut + '\'' +
				'}';
	}
}
