<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.clinique.gestion_clinique.entity.Patient" %>

        <% Patient patient=(Patient) request.getAttribute("patient"); String erreur=(String)
            request.getAttribute("erreur"); %>

            <!DOCTYPE html>
            <html lang="fr">

            <head>
                <meta charset="UTF-8">
                <title>Consultation du patient</title>

                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f4f6f8;
                        margin: 0;
                        padding: 30px;
                    }

                    .container {
                        max-width: 900px;
                        margin: auto;
                        background: white;
                        padding: 30px;
                        border-radius: 10px;
                        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
                    }

                    h1 {
                        margin-top: 0;
                        color: #333;
                    }

                    h2 {
                        margin-top: 30px;
                        color: #444;
                        border-bottom: 1px solid #ddd;
                        padding-bottom: 8px;
                    }

                    .error {
                        background-color: #ffe5e5;
                        color: #b00020;
                        padding: 12px;
                        border-radius: 6px;
                        margin-bottom: 20px;
                    }

                    .patient-info {
                        display: grid;
                        grid-template-columns: 1fr 1fr;
                        gap: 15px;
                        background-color: #f8f9fa;
                        padding: 20px;
                        border-radius: 8px;
                    }

                    .info-item {
                        padding: 8px;
                    }

                    .info-item strong {
                        display: block;
                        color: #555;
                        margin-bottom: 4px;
                    }

                    .form-group {
                        margin-bottom: 20px;
                    }

                    label {
                        display: block;
                        font-weight: bold;
                        margin-bottom: 7px;
                        color: #333;
                    }

                    input[type="text"],
                    textarea {
                        width: 100%;
                        box-sizing: border-box;
                        padding: 10px;
                        border: 1px solid #ccc;
                        border-radius: 6px;
                        font-size: 15px;
                    }

                    textarea {
                        min-height: 120px;
                        resize: vertical;
                    }

                    input[readonly] {
                        background-color: #eee;
                    }

                    .cost {
                        background-color: #eef6ff;
                        padding: 15px;
                        border-radius: 6px;
                        margin: 20px 0;
                        font-size: 18px;
                        font-weight: bold;
                    }

                    .actions {
                        display: flex;
                        gap: 10px;
                        margin-top: 25px;
                    }

                    .btn {
                        display: inline-block;
                        padding: 12px 20px;
                        border: none;
                        border-radius: 6px;
                        cursor: pointer;
                        text-decoration: none;
                        font-size: 15px;
                    }

                    .btn-primary {
                        background-color: #198754;
                        color: white;
                    }

                    .btn-secondary {
                        background-color: #6c757d;
                        color: white;
                    }

                    .btn-primary:hover {
                        background-color: #157347;
                    }

                    .btn-secondary:hover {
                        background-color: #5c636a;
                    }
                </style>
            </head>

            <body>

                <div class="container">

                    <h1>Consultation du patient</h1>

                    <% if (erreur !=null) { %>
                        <div class="error">
                            <%= erreur %>
                        </div>
                        <% } %>

                            <% if (patient==null) { %>

                                <div class="error">
                                    Patient introuvable.
                                </div>

                                <a href="<%= request.getContextPath() %>/generaliste/patients"
                                    class="btn btn-secondary">
                                    Retour à la liste
                                </a>

                                <% } else { %>

                                    <!-- ========================= -->
                                    <!-- INFORMATIONS DU PATIENT -->
                                    <!-- ========================= -->

                                    <h2>Informations du patient</h2>

                                    <div class="patient-info">

                                        <div class="info-item">
                                            <strong>Nom</strong>
                                            <%= patient.getNom() %>
                                        </div>

                                        <div class="info-item">
                                            <strong>Prénom</strong>
                                            <%= patient.getPrenom() %>
                                        </div>

                                        <div class="info-item">
                                            <strong>Date de naissance</strong>
                                            <%= patient.getDateNaissance() %>
                                        </div>

                                        <div class="info-item">
                                            <strong>Numéro de sécurité sociale</strong>
                                            <%= patient.getNumSecu() %>
                                        </div>

                                        <div class="info-item">
                                            <strong>Heure d'arrivée</strong>
                                            <%= patient.getHeureArrivee() %>
                                        </div>

                                        <div class="info-item">
                                            <strong>Statut</strong>
                                            <%= patient.getStatut() %>
                                        </div>

                                    </div>


                                    <!-- ========================= -->
                                    <!-- SIGNES VITAUX -->
                                    <!-- ========================= -->

                                    <h2>Signes vitaux</h2>

                                    <div class="patient-info">

                                        <div class="info-item">
                                            <strong>Tension artérielle</strong>
                                            <%= patient.getTension() %>
                                        </div>

                                        <div class="info-item">
                                            <strong>Fréquence cardiaque</strong>
                                            <%= patient.getFrequenceCardiaque() %> bpm
                                        </div>

                                        <div class="info-item">
                                            <strong>Température</strong>
                                            <%= patient.getTemperature() %> °C
                                        </div>

                                        <div class="info-item">
                                            <strong>Fréquence respiratoire</strong>
                                            <%= patient.getFrequenceRespiratoire() %> /min
                                        </div>

                                    </div>


                                    <!-- ========================= -->
                                    <!-- FORMULAIRE CONSULTATION -->
                                    <!-- ========================= -->

                                    <h2>Consultation médicale</h2>

                                    <form method="post"
                                        action="<%= request.getContextPath() %>/generaliste/consultation">

                                        <!-- ID du patient -->
                                        <input type="hidden" name="patientId" value="${patient.id}">

                                        <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}">


                                        <!-- MOTIF -->

                                        <div class="form-group">

                                            <label for="motif">
                                                Motif de consultation
                                            </label>

                                            <input type="text" id="motif" name="motif"
                                                placeholder="Ex : Douleur abdominale, fièvre..."
                                                value="<%= request.getParameter(" motif") !=null ?
                                                request.getParameter("motif") : "" %>"
                                            required>

                                        </div>


                                        <!-- OBSERVATIONS -->

                                        <div class="form-group">

                                            <label for="observations">
                                                Observations / Examen clinique
                                            </label>

                                            <textarea id="observations" name="observations"
                                                placeholder="Décrire les symptômes et les observations cliniques..."
                                                required><%= request.getParameter("observations") != null
                            ? request.getParameter("observations")
                            : "" %></textarea>

                                        </div>


                                        <!-- DIAGNOSTIC -->

                                        <div class="form-group">

                                            <label for="diagnostic">
                                                Diagnostic
                                            </label>

                                            <textarea id="diagnostic" name="diagnostic"
                                                placeholder="Saisir le diagnostic..." required><%= request.getParameter("diagnostic") != null
                            ? request.getParameter("diagnostic")
                            : "" %></textarea>

                                        </div>


                                        <!-- TRAITEMENT -->

                                        <div class="form-group">

                                            <label for="traitement">
                                                Traitement prescrit
                                            </label>

                                            <textarea id="traitement" name="traitement"
                                                placeholder="Saisir le traitement prescrit..." required><%= request.getParameter("traitement") != null
                            ? request.getParameter("traitement")
                            : "" %></textarea>

                                        </div>


                                        <!-- COUT FIXE -->

                                        <div class="cost">
                                            Coût de la consultation : 150 DH
                                        </div>


                                        <!-- ACTIONS -->

                                        <div class="actions">

                                            <button type="submit" class="btn btn-primary">
                                                Clôturer la consultation
                                            </button>

                                            <a href="<%= request.getContextPath() %>/generaliste/patients"
                                                class="btn btn-secondary">
                                                Annuler
                                            </a>

                                        </div>

                                    </form>

                                    <% } %>

                </div>

            </body>

            </html>