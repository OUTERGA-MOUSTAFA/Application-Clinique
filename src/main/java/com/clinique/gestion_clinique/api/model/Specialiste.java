package com.clinique.gestion_clinique.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "Specialistes")
public class Specialiste {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "utilisateur_id", nullable = false)
    private Long utilisateurId;

    @Enumerated (EnumType.STRING)
    @Column(nullable = false)
    private Specialiste specialite;

    @Column(nullable = false)
    private Double tarif;

    public Specialiste() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }

    public Specialiste getSpecialite() { return specialite; }
    public void setSpecialite(Specialiste specialite) { this.specialite = specialite; }

    public Double getTarif() { return tarif; }
    public void setTarif(Double tarif) { this.tarif = tarif; }
}
