package com.clinique.gestion_clinique.api.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity 
@Table (name = "demandes_expertise")
public class DemandeExpertise {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "consultation_id", nullable = false)
    private Long consultationId;

    @Column(name = "specialiste_id", nullable = false)
    private Long specialisteId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Enumerated (EnumType.STRING)
    @Column(nullable = false)
    private Priorite priorite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDemande statut;

    @Column(columnDefinition = "TEXT")
    private String avis;

    @Column(columnDefinition = "TEXT")
    private String recommandations;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    public DemandeExpertise() {}

    @PrePersist 
    public void prePersist() {
        this.dateCreation = LocalDateTime.now();
        if (this.statut == null) {
            this.statut = StatutDemande.EN_ATTENTE;
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getConsultationId() { return consultationId; }
    public void setConsultationId(Long consultationId) { this.consultationId = consultationId; }
    public Long getSpecialisteId() { return specialisteId; }
    public void setSpecialisteId(Long specialisteId) { this.specialisteId = specialisteId; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public Priorite getPriorite() { return priorite; }
    public void setPriorite(Priorite priorite) { this.priorite = priorite; }
    public StatutDemande getStatut() { return statut; }
    public void setStatut(StatutDemande statut) { this.statut = statut; }
    public String getAvis() { return avis; }
    public void setAvis(String avis) { this.avis = avis; }
    public String getRecommandations() { return recommandations; }
    public void setRecommandations(String recommandations) { this.recommandations = recommandations; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
}
