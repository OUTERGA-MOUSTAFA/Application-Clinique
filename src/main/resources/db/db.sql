-- Suppression des tables si elles existent (pour éviter les erreurs)
DROP TABLE IF EXISTS consultation;
DROP TABLE IF EXISTS patient;
DROP TABLE IF EXISTS utilisateur;

-- 1. Table Utilisateur
CREATE TABLE utilisateur (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL -- 'INFIRMIER' ou 'GENERALISTE'
);

-- 2. Table Patient
CREATE TABLE patient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance DATE NOT NULL,
    nss VARCHAR(50) UNIQUE NOT NULL,
    tension_arterielle VARCHAR(20),
    frequence_cardiaque INT,
    temperature DECIMAL(4,2),
    frequence_respiratoire INT,
    date_arrivee DATETIME NOT NULL
);

-- 3. Table Consultation
CREATE TABLE consultation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    medecin_id BIGINT NOT NULL,
    motif TEXT,
    observations TEXT,
    diagnostic TEXT,
    traitement TEXT,
    cout DECIMAL(10,2) NOT NULL DEFAULT 150.00,
    statut VARCHAR(50) NOT NULL DEFAULT 'TERMINEE',
    date_consultation DATETIME NOT NULL,
    CONSTRAINT fk_consultation_patient FOREIGN KEY (patient_id) REFERENCES patient(id),
    CONSTRAINT fk_consultation_medecin FOREIGN KEY (medecin_id) REFERENCES utilisateur(id)
);