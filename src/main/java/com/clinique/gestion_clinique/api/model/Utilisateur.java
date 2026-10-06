package com.clinique.gestion_clinique.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "utilisateur")
public class Utilisateur {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String nom;
    private String prenom;

    private String mot_de_passe;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Utilisateur(){}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom(){
        return nom;
    }

    public String getPrenom(){
        return prenom;
    }
    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return mot_de_passe;
    }

    public Role getRole() {
        return role;
    }


    
    public void setEmail(String email) {
        this.email = email;
    }

    public void  setNom(String nom){
        this.nom = nom;
    }
    public void  setPrenom(String prenom){
        this.prenom = prenom;
    }

    public void setMotDePasse(String motDePasse) {
        this.mot_de_passe = motDePasse;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
