# 🏥 Système de Télé-expertise Médicale : Application Clinique

## 📝 Description
Ce projet est une application web Java EE développée dans le cadre du **Sprint 2 - Brief 1**. Elle permet la gestion de l'accueil des patients par un infirmier et leur consultation par un médecin généraliste.

L'application est construite selon une **architecture en couches stricte** (Controller, Service, DAO, Entity) afin de faciliter la migration future vers un service de télé-expertise et de garantir la maintenabilité du code.

## ✨ Fonctionnalités (User Stories)
- **Authentification :** Login/Logout  sécurisé, gestion des rôles (Infirmier, Généraliste), protection CSRF, mots de passe hachés en BCrypt.
- **Module Infirmier (US1 & US2) :**
  - Enregistrement des patients avec identité et signes vitaux (TA, FC, Température, FR).
  - Enregistrement automatique de l'heure d'arrivée.
  - Affichage de la liste des patients du jour triée par heure d'arrivée (utilisation de la Stream API).
- **Module Médecin Généraliste (US3) :**
  - Consultation de la liste des patients en attente.
  - Saisie d'un formulaire unique (motif, observations, diagnostic, traitement).
  - Clôture de la consultation avec un coût fixe de 150 DH et un statut TERMINEE.
- **Bonus :**
  - Recherche de patient par NSS pour mise à jour des signes vitaux.
  - Ajout d'actes techniques (radiographie, échographie, etc.) avec calcul du coût total via `map().sum()`.

## 🏗️ Architecture Technique
Le projet respecte une séparation stricte des responsabilités :
- **Controller :** Servlets Jakarta EE.
- **Service :** Logique métier et validation.
- **DAO (Repository) :** Accès aux données via des interfaces (`PatientDAO`, `UtilisateurDAO`, `ConsultationDAO`).
- **Entity :** Modèle de données (`Utilisateur`, `Patient`, `Consultation`).

**Note sur les livrables :**
- **Livrable 1 :** Implémentation JDBC des DAO (`JdbcUserDAO`, `JdbcPatientDAO`, `JdbcConsultationDAO`).
- **Livrable 2 :** Migration vers JPA/Hibernate (`JpaUserDAO`, `JpaPatientDAO`, `JpaConsultationDAO`).

### Flux d'accès aux données (Livrable 1)

```mermaid
flowchart LR
  Servlet --> Service
  Service --> DAO[DAO (interface)]
  DAO --> JdbcDAO[JdbcXxxDAO]
  JdbcDAO --> DataSource
  DataSource --> Database[(MySQL / PostgreSQL)]
```

Pour le Livrable 1, les implémentations `JdbcXxxDAO` exécutent elles-mêmes le SQL via JDBC, avec un `DataSource`, des `PreparedStatement` et la fermeture des ressources avec try-with-resources. JPA/Hibernate n'est pas utilisé dans cette livraison.

## 🛠️ Stack Technique
- **Langage :** Java 17+
- **Build :** Maven
- **Web :** Jakarta EE (Servlet, JSP, JSTL), Apache Tomcat 10+
- **Base de données :** MySQL ou PostgreSQL
- **ORM :** Hibernate / JPA
- **Sécurité :** BCrypt (jBCrypt), Filtres HTTP (Session, CSRF)
- **IDE :** Visual Studio Code

## 📂 Structure du Projet
```text
gestion-clinique/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/clinique/
│   │   │       ├── entity/       # Entités JPA
│   │   │       ├── repository/   # Interfaces DAO et implémentations (JDBC/JPA)
│   │   │       ├── service/      # Logique métier
│   │   │       ├── servlet/      # Contrôleurs
│   │   │       └── filter/       # Filtres (Auth, CSRF)
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       └── persistence.xml # Config JPA (Livrable 2)
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── web.xml
│   │       ├── infirmier/        # JSP Infirmier
│   │       ├── generaliste/      # JSP Généraliste
│   │       └── login.jsp
└── pom.xml