package com.clinique.gestion_clinique.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "consultation")
public class Consultation {

	 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "medecin_id", nullable = false)
	private Utilisateur medecin;
	private String motif;
	private String observations;
	private String diagnostic;
	private String traitement;
	private BigDecimal cout;
	
	@Enumerated(EnumType.STRING)
    private Statut statut;

    @Column(name = "date_consultation")
    private LocalDateTime dateConsultation;

	public Consultation() {
	}

	public Consultation(Long id, Patient patient, Utilisateur medecin, String motif, String observations,
			String diagnostic, String traitement, BigDecimal cout, Statut statut, LocalDateTime dateConsultation) {
		this.id = id;
		this.patient = patient;
		this.medecin = medecin;
		this.motif = motif;
		this.observations = observations;
		this.diagnostic = diagnostic;
		this.traitement = traitement;
		this.cout = cout;
		this.statut = statut;
		this.dateConsultation = dateConsultation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Utilisateur getMedecin() {
		return medecin;
	}

	public void setMedecin(Utilisateur medecin) {
		this.medecin = medecin;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public String getDiagnostic() {
		return diagnostic;
	}

	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}

	public String getTraitement() {
		return traitement;
	}

	public void setTraitement(String traitement) {
		this.traitement = traitement;
	}

	public BigDecimal getCout() {
		return cout;
	}

	public void setCout(BigDecimal cout) {
		this.cout = cout;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public LocalDateTime getDateConsultation() {
		return dateConsultation;
	}

	public void setDateConsultation(LocalDateTime dateConsultation) {
		this.dateConsultation = dateConsultation;
	}

	@Override
	public String toString() {
		return "Consultation{" +
				"id=" + id +
				", statut=" + statut +
				", dateConsultation=" + dateConsultation +
				'}';
	}
}
