Application Clinique - Système de Télé-expertise Médicale
English version below

📋 Description
Application web de gestion pour une clinique, développée dans le cadre d'un brief de projet Java. Elle permet la gestion du parcours patient, de l'accueil par un infirmier jusqu'à la consultation par un médecin généraliste.

Ce projet est un support d'apprentissage pour :

La mise en place d'une architecture en couches propre (Controller, Service, DAO).

L'implémentation d'une authentification stateful avec gestion de rôles.

La comparaison entre un accès aux données en JDBC et une migration vers JPA/Hibernate.

L'application de bonnes pratiques de sécurité (hachage de mot de passe, protection CSRF).

✨ Fonctionnalités
Authentification : Login/Logout avec deux rôles (Infirmier, Généraliste).

Module Infirmier :

Enregistrer un nouveau patient (identité + signes vitaux).

Enregistrer automatiquement l'heure d'arrivée.

Visualiser la liste des patients du jour, triée par heure d'arrivée.

Module Médecin Généraliste :

Voir la liste des patients en attente de consultation.

Consulter les signes vitaux d'un patient.

Remplir le formulaire de consultation (motif, observations, diagnostic, traitement).

Clôturer la consultation avec un coût fixe de 150 DH.

Sécurité : Mots de passe hachés avec bcrypt, protection CSRF sur les formulaires POST.

🛠️ Stack Technique
Composant	Technologie
Langage	Java 17+
Build	Maven
Web	Jakarta EE, Servlet, JSP, JSTL
Serveur	Apache Tomcat 10+
Base de données	MySQL / PostgreSQL
Persistance	JDBC (Livrable 1) → JPA/Hibernate (Livrable 2)
Sécurité	bcrypt, Filtre CSRF
Architecture	MVC en couches (Controller → Service → Repository → Entity)
📁 Structure du Projet
text
Application-Clinique/
├── .github/                          # Hooks de modernisation GitHub
│   └── modernize/java-upgrade/hooks/scripts/
├── .mvn/wrapper/                     # Maven Wrapper
├── .vscode/                          # Configuration VS Code
├── docs/                             # Documentation
├── src/
│   ├── main/
│   │   ├── java/com/clinique/gestion_clinique/
│   │   │   ├── config/               # Configuration (DataSource, JPA)
│   │   │   ├── controller/
│   │   │   │   ├── generaliste/      # Servlets pour le médecin
│   │   │   │   └── patient/          # Servlets pour l'infirmier
│   │   │   ├── entity/               # Entités JPA (Utilisateur, Patient, Consultation)
│   │   │   ├── filter/               # Filtres (Auth, CSRF)
│   │   │   ├── repository/
│   │   │   │   └── jdbc/             # Implémentations JDBC des DAO
│   │   │   └── service/              # Logique métier
│   │   ├── resources/
│   │   │   ├── db/                   # Scripts SQL (création, données)
│   │   │   ├── static/               # CSS, JS
│   │   │   └── templates/            # Templates JSP (si utilisés)
│   │   └── webapp/
│   │       ├── META-INF/
│   │       └── WEB-INF/
│   │           └── views/
│   │               ├── fragments/    # En-têtes, pieds de page JSP
│   │               ├── generaliste/  # Vues du médecin
│   │               ├── infirmier/    # Vues de l'infirmier
│   │               └── patients/     # Vues partagées
│   └── test/
│       └── java/com/clinique/gestion_clinique/
│           └── repository/jdbc/      # Tests des DAO JDBC
└── target/                           # Fichiers générés (build)
    ├── classes/
    ├── gestion-clinique/             # WAR packagé
    └── ...
🚀 Démarrage Rapide
Prérequis
JDK 17 ou supérieur.

Maven (le wrapper mvnw est inclus).

Apache Tomcat 10 ou supérieur (compatible Jakarta EE).

MySQL ou PostgreSQL (une instance locale).

Étapes d'Installation
Cloner le dépôt

bash
git clone https://github.com/OUTERGA-MOUSTAFA/Application-Clinique.git
cd Application-Clinique
Configurer la base de données

Créer une base de données nommée gestion_clinique.

Exécuter les scripts SQL se trouvant dans src/main/resources/db/ pour créer les tables et insérer les utilisateurs par défaut.

Mettre à jour les identifiants de connexion dans le fichier de configuration (par exemple, src/main/java/com/clinique/gestion_clinique/config/DataSourceConfig.java ou un fichier persistence.xml si vous êtes en JPA).

Construire le projet

bash
./mvnw clean package
Cela générera un fichier WAR dans le répertoire target/.

Déployer sur Tomcat

Copier le fichier WAR (gestion-clinique.war) dans le répertoire webapps/ de Tomcat.

Démarrer Tomcat.

Accéder à l'application via http://localhost:8080/gestion-clinique/.

⚠️ Problèmes et Solutions
Problème	Cause Probable	Solution
Erreur 404 au démarrage	Le contexte de l'application n'est pas correct.	Vérifier le nom du WAR déployé. L'URL doit correspondre au nom du fichier (sans .war).
ClassNotFoundException: com.mysql.cj.jdbc.Driver	Le driver JDBC n'est pas inclus dans le build.	S'assurer que la dépendance mysql-connector-j (ou PostgreSQL) est présente dans le pom.xml avec le scope runtime ou compile.
Access denied for user	Identifiants de base de données incorrects.	Vérifier les propriétés de connexion dans la configuration.
Les JSP ne s'affichent pas (code source visible)	La dépendance JSTL est manquante ou mal configurée.	Ajouter la dépendance jakarta.servlet.jsp.jstl-api et son implémentation (org.glassfish.web:jakarta.servlet.jsp.jstl) dans le pom.xml.
Échec de la protection CSRF	Le token n'est pas inclus dans le formulaire ou le filtre n'est pas correctement configuré.	Vérifier que chaque formulaire POST inclut <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}"> et que le filtre CSRF est bien mappé.
Migration JDBC → JPA	Les DAO JDBC et JPA ne peuvent pas coexister facilement.	Suivre l'architecture en couches : les services doivent dépendre uniquement des interfaces DAO. Il suffit alors de remplacer les implémentations Jdbc*DAO par des Jpa*DAO dans la configuration (ex. via une factory ou l'injection de dépendances).
🤝 Collaboration
Ce projet est avant tout un outil d'apprentissage. Les contributions, suggestions et corrections sont les bienvenues.

Comment Contribuer
Fork le dépôt.

Créer une branche pour votre fonctionnalité (git checkout -b feature/ma-fonctionnalite).

Commiter vos changements (git commit -m 'Ajout de ma fonctionnalité').

Pousser la branche (git push origin feature/ma-fonctionnalite).

Ouvrir une Pull Request.

Bonnes Pratiques
Respecter l'architecture en couches existante.

Écrire des tests unitaires pour les nouvelles implémentations de DAO.

Documenter les méthodes publiques avec Javadoc.

Suivre les conventions de nommage Java (CamelCase, etc.).

📄 Licence
Ce projet est distribué sous licence MIT. Voir le fichier LICENSE pour plus d'informations.

English Version
📋 Description
Web application for clinic management, developed as part of a Java project brief. It handles the patient journey, from nurse admission to general practitioner consultation.

This project serves as a learning tool for:

Implementing a clean layered architecture (Controller, Service, DAO).

Setting up stateful authentication with role-based access.

Comparing JDBC data access with a migration to JPA/Hibernate.

Applying security best practices (password hashing, CSRF protection).

✨ Features
Authentication: Login/Logout with two roles (Nurse, General Practitioner).

Nurse Module:

Register a new patient (identity + vital signs).

Automatically record arrival time.

View the day's patient list, sorted by arrival time.

General Practitioner Module:

View patients waiting for consultation.

Check a patient's vital signs.

Fill out the consultation form (reason, observations, diagnosis, treatment).

Close the consultation with a fixed cost of 150 DH.

Security: Passwords hashed with bcrypt, CSRF protection on POST forms.

🛠️ Tech Stack
Component	Technology
Language	Java 17+
Build	Maven
Web	Jakarta EE, Servlet, JSP, JSTL
Server	Apache Tomcat 10+
Database	MySQL / PostgreSQL
Persistence	JDBC (Deliverable 1) → JPA/Hibernate (Deliverable 2)
Security	bcrypt, CSRF Filter
Architecture	Layered MVC (Controller → Service → Repository → Entity)
📁 Project Structure
text
Application-Clinique/
├── .github/                          # GitHub modernization hooks
│   └── modernize/java-upgrade/hooks/scripts/
├── .mvn/wrapper/                     # Maven Wrapper
├── .vscode/                          # VS Code configuration
├── docs/                             # Documentation
├── src/
│   ├── main/
│   │   ├── java/com/clinique/gestion_clinique/
│   │   │   ├── config/               # Configuration (DataSource, JPA)
│   │   │   ├── controller/
│   │   │   │   ├── generaliste/      # Servlets for the doctor
│   │   │   │   └── patient/          # Servlets for the nurse
│   │   │   ├── entity/               # JPA Entities (User, Patient, Consultation)
│   │   │   ├── filter/               # Filters (Auth, CSRF)
│   │   │   ├── repository/
│   │   │   │   └── jdbc/             # JDBC DAO implementations
│   │   │   └── service/              # Business logic
│   │   ├── resources/
│   │   │   ├── db/                   # SQL scripts (schema, data)
│   │   │   ├── static/               # CSS, JS
│   │   │   └── templates/            # JSP templates (if any)
│   │   └── webapp/
│   │       ├── META-INF/
│   │       └── WEB-INF/
│   │           └── views/
│   │               ├── fragments/    # JSP headers, footers
│   │               ├── generaliste/  # Doctor views
│   │               ├── infirmier/    # Nurse views
│   │               └── patients/     # Shared views
│   └── test/
│       └── java/com/clinique/gestion_clinique/
│           └── repository/jdbc/      # JDBC DAO tests
└── target/                           # Generated files (build)
    ├── classes/
    ├── gestion-clinique/             # Packaged WAR
    └── ...
🚀 Quick Start
Prerequisites
JDK 17 or higher.

Maven (the mvnw wrapper is included).

Apache Tomcat 10 or higher (Jakarta EE compatible).

MySQL or PostgreSQL (local instance).

Installation Steps
Clone the repository

bash
git clone https://github.com/OUTERGA-MOUSTAFA/Application-Clinique.git
cd Application-Clinique
Configure the database

Create a database named gestion_clinique.

Run the SQL scripts located in src/main/resources/db/ to create tables and insert default users.

Update connection credentials in the configuration file (e.g., src/main/java/com/clinique/gestion_clinique/config/DataSourceConfig.java or a persistence.xml file if using JPA).

Build the project

bash
./mvnw clean package
This will generate a WAR file in the target/ directory.

Deploy to Tomcat

Copy the WAR file (gestion-clinique.war) to Tomcat's webapps/ directory.

Start Tomcat.

Access the application at http://localhost:8080/gestion-clinique/.

⚠️ Problems and Solutions
Problem	Likely Cause	Solution
404 error on startup	Incorrect application context.	Check the deployed WAR name. The URL should match the file name (without .war).
ClassNotFoundException: com.mysql.cj.jdbc.Driver	JDBC driver not included in the build.	Ensure the mysql-connector-j (or PostgreSQL) dependency is in pom.xml with runtime or compile scope.
Access denied for user	Incorrect database credentials.	Verify connection properties in the configuration.
JSPs not rendering (source code visible)	JSTL dependency missing or misconfigured.	Add the jakarta.servlet.jsp.jstl-api dependency and its implementation (org.glassfish.web:jakarta.servlet.jsp.jstl) to pom.xml.
CSRF protection failure	Token not included in form or filter not properly configured.	Ensure each POST form includes <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}"> and the CSRF filter is correctly mapped.
JDBC → JPA Migration	JDBC and JPA DAOs can't easily coexist.	Follow the layered architecture: services must depend only on DAO interfaces. Then simply replace Jdbc*DAO implementations with Jpa*DAO in the configuration (e.g., via a factory or dependency injection).
🤝 Collaboration
This project is primarily a learning tool. Contributions, suggestions, and corrections are welcome.

How to Contribute
Fork the repository.

Create a branch for your feature (git checkout -b feature/my-feature).

Commit your changes (git commit -m 'Add my feature').

Push the branch (git push origin feature/my-feature).

Open a Pull Request.

Best Practices
Respect the existing layered architecture.

Write unit tests for new DAO implementations.

Document public methods with Javadoc.

Follow Java naming conventions (CamelCase, etc.).

📄 License
This project is distributed under the MIT License. See the LICENSE file for more information.