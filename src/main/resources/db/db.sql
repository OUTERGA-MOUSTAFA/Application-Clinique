-- Suppression des tables si elles existent (pour éviter les erreurs)
DROP TABLE IF EXISTS consultation;
DROP TABLE IF EXISTS patient;
DROP TABLE IF EXISTS utilisateur;

-- 1. Table Utilisateur

CREATE TABLE utilisateur (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);
-- 2. Table Patient
CREATE TABLE patient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance DATE NOT NULL,
    num_secu VARCHAR(50),
    tension VARCHAR(20),
    frequence_cardiaque INT,
    temperature DOUBLE,
    frequence_respiratoire INT,
    heure_arrivee DATETIME NOT NULL,
    statut VARCHAR(30) NOT NULL
);

-- 3. Table Consultation
CREATE TABLE consultation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    patient_id BIGINT NOT NULL UNIQUE,

    medecin_id BIGINT NOT NULL,

    motif VARCHAR(255) NOT NULL,

    observations TEXT,

    diagnostic TEXT NOT NULL,

    traitement TEXT NOT NULL,

    cout DECIMAL(10,2) NOT NULL,

    statut VARCHAR(30) NOT NULL,

    date_consultation DATETIME NOT NULL,

    CONSTRAINT fk_consultation_patient
        FOREIGN KEY (patient_id)
        REFERENCES patient(id),

    CONSTRAINT fk_consultation_medecin
        FOREIGN KEY (medecin_id)
        REFERENCES utilisateur(id)
);
-- entre des comptes par defaut de nurse et doctor
INSERT INTO utilisateur
    (nom, prenom, email, mot_de_passe, role)
VALUES
(
    'Infirmier',
    'Test',
    'infirmier@clinique.com',
    '$2a$10$e8R5qQW3h4L.OaYqW8/R4.uPq5Yh7GqI7k8Y3e7l5O7S9/mKz2/2i',
    'INFIRMIER'
),
(
    'Medecin',
    'Test',
    'medecin@clinique.com',
    '$2a$10$e8R5qQW3h4L.OaYqW8/R4.uPq5Yh7GqI7k8Y3e7l5O7S9/mKz2/2i',
    'GENERALISTE'
);