<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.clinique.gestion_clinique.entity.Patient" %>

        <% Patient patient=(Patient) request.getAttribute("patient"); String erreur=(String)
            request.getAttribute("erreur"); %>

            <!DOCTYPE html>
            <html lang="fr">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Consultation du patient</title>

                <style>
                    * {
                        margin: 0;
                        padding: 0;
                        box-sizing: border-box;
                    }

                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        background: #f0f4f8;
                        color: #2d3748;
                        padding: 2rem 1rem;
                        min-height: 100vh;
                    }

                    .container {
                        max-width: 900px;
                        margin: 0 auto;
                    }

                    .card {
                        background: #ffffff;
                        border-radius: 12px;
                        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
                        padding: 2rem;
                        margin-bottom: 1.5rem;
                    }

                    h1 {
                        color: #2c5282;
                        font-size: 1.8rem;
                        margin-bottom: 1.5rem;
                        padding-bottom: 1rem;
                        border-bottom: 3px solid #4299e1;
                        display: flex;
                        align-items: center;
                        gap: 0.75rem;
                    }

                    h1::before {
                        content: "🩺";
                        font-size: 2rem;
                    }

                    h2 {
                        color: #2c5282;
                        font-size: 1.2rem;
                        margin-bottom: 1.25rem;
                        padding-bottom: 0.5rem;
                        border-bottom: 2px solid #e2e8f0;
                        display: flex;
                        align-items: center;
                        gap: 0.5rem;
                    }

                    h2.section-info::before {
                        content: "👤";
                    }

                    h2.section-vitaux::before {
                        content: "💓";
                    }

                    h2.section-consultation::before {
                        content: "✍️";
                    }

                    /* Erreur */
                    .alert-error {
                        background: #fed7d7;
                        color: #c53030;
                        border-left: 4px solid #e53e3e;
                        padding: 1rem 1.25rem;
                        border-radius: 8px;
                        margin-bottom: 1.5rem;
                        font-weight: 500;
                        display: flex;
                        align-items: center;
                        gap: 0.5rem;
                    }

                    /* Fiche patient */
                    .patient-info {
                        display: grid;
                        grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
                        gap: 1rem;
                    }

                    .info-item {
                        background: #f7fafc;
                        border-left: 3px solid #4299e1;
                        padding: 0.875rem 1rem;
                        border-radius: 6px;
                    }

                    .info-item .label {
                        display: block;
                        font-size: 0.75rem;
                        font-weight: 600;
                        color: #718096;
                        text-transform: uppercase;
                        letter-spacing: 0.5px;
                        margin-bottom: 0.25rem;
                    }

                    .info-item .value {
                        display: block;
                        font-size: 1rem;
                        font-weight: 600;
                        color: #2d3748;
                    }

                    /* Signes vitaux mis en avant */
                    .info-item.vital {
                        background: #ebf8ff;
                        border-left-color: #2c5282;
                    }

                    .info-item.vital .value {
                        color: #2c5282;
                        font-size: 1.1rem;
                    }

                    /* Formulaire */
                    .form-group {
                        margin-bottom: 1.25rem;
                    }

                    label {
                        display: block;
                        color: #4a5568;
                        font-size: 0.875rem;
                        font-weight: 600;
                        margin-bottom: 0.5rem;
                    }

                    input[type="text"],
                    textarea {
                        width: 100%;
                        padding: 0.75rem 1rem;
                        border: 2px solid #e2e8f0;
                        border-radius: 8px;
                        font-size: 0.95rem;
                        color: #2d3748;
                        background: #f7fafc;
                        transition: all 0.2s ease;
                        font-family: inherit;
                        resize: vertical;
                    }

                    input[type="text"]:focus,
                    textarea:focus {
                        outline: none;
                        border-color: #4299e1;
                        background: #ffffff;
                        box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.15);
                    }

                    textarea {
                        min-height: 110px;
                        line-height: 1.5;
                    }

                    /* Coût de la consultation */
                    .cost {
                        background: linear-gradient(135deg, #ebf8ff, #bee3f8);
                        color: #2c5282;
                        padding: 1rem 1.25rem;
                        border-radius: 8px;
                        margin: 1.5rem 0;
                        font-size: 1.05rem;
                        font-weight: 700;
                        display: flex;
                        align-items: center;
                        gap: 0.5rem;
                    }

                    .cost::before {
                        content: "💰";
                        font-size: 1.3rem;
                    }

                    /* Actions */
                    .actions {
                        display: flex;
                        gap: 0.75rem;
                        margin-top: 1.5rem;
                        flex-wrap: wrap;
                    }

                    .btn {
                        display: inline-flex;
                        align-items: center;
                        justify-content: center;
                        gap: 0.5rem;
                        padding: 0.875rem 1.5rem;
                        border: none;
                        border-radius: 8px;
                        cursor: pointer;
                        text-decoration: none;
                        font-size: 0.95rem;
                        font-weight: 600;
                        font-family: inherit;
                        transition: all 0.2s ease;
                        letter-spacing: 0.3px;
                    }

                    .btn-primary {
                        background: linear-gradient(135deg, #2c5282, #4299e1);
                        color: #ffffff;
                        flex: 1;
                    }

                    .btn-primary:hover {
                        transform: translateY(-2px);
                        box-shadow: 0 8px 20px rgba(66, 153, 225, 0.4);
                    }

                    .btn-primary:active {
                        transform: translateY(0);
                    }

                    .btn-secondary {
                        background: #ffffff;
                        color: #4a5568;
                        border: 2px solid #e2e8f0;
                    }

                    .btn-secondary:hover {
                        border-color: #cbd5e0;
                        background: #f7fafc;
                        color: #2c5282;
                    }

                    /* Responsive */
                    @media (max-width: 600px) {
                        body {
                            padding: 1rem 0.5rem;
                        }

                        .card {
                            padding: 1.25rem;
                        }

                        h1 {
                            font-size: 1.4rem;
                        }

                        .patient-info {
                            grid-template-columns: 1fr;
                        }

                        .actions {
                            flex-direction: column;
                        }

                        .btn {
                            width: 100%;
                        }
                    }
                </style>
            </head>

            <body>

                <%-- Header de navigation (hors du container, en pleine largeur) --%>
                    <%@ include file="/WEB-INF/views/fragments/header.jsp" %>

                        <div class="container">

                            <div class="card">
                                <h1>Consultation du patient</h1>

                                <%-- Message d'erreur --%>
                                    <% if (erreur !=null) { %>
                                        <div class="alert-error">⚠️ <%= erreur %>
                                        </div>
                                        <% } %>

                                            <% if (patient==null) { %>

                                                <div class="alert-error">⚠️ Patient introuvable.</div>

                                                <a href="<%= request.getContextPath() %>/generaliste/patients"
                                                    class="btn btn-secondary">
                                                    ← Retour à la liste
                                                </a>

                                                <% } else { %>

                                                    <!-- ========================= -->
                                                    <!-- INFORMATIONS DU PATIENT   -->
                                                    <!-- ========================= -->

                                                    <h2 class="section-info">Informations du patient</h2>

                                                    <div class="patient-info">
                                                        <div class="info-item">
                                                            <span class="label">Nom</span>
                                                            <span class="value">
                                                                <%= patient.getNom() %>
                                                            </span>
                                                        </div>
                                                        <div class="info-item">
                                                            <span class="label">Prénom</span>
                                                            <span class="value">
                                                                <%= patient.getPrenom() %>
                                                            </span>
                                                        </div>
                                                        <div class="info-item">
                                                            <span class="label">Date de naissance</span>
                                                            <span class="value">
                                                                <%= patient.getDateNaissance() %>
                                                            </span>
                                                        </div>
                                                        <div class="info-item">
                                                            <span class="label">N° sécurité sociale</span>
                                                            <span class="value">
                                                                <%= patient.getNumSecu() %>
                                                            </span>
                                                        </div>
                                                        <div class="info-item">
                                                            <span class="label">Heure d'arrivée</span>
                                                            <span class="value">
                                                                <%= patient.getHeureArrivee() %>
                                                            </span>
                                                        </div>
                                                        <div class="info-item">
                                                            <span class="label">Statut</span>
                                                            <span class="value">
                                                                <%= patient.getStatut() %>
                                                            </span>
                                                        </div>
                                                    </div>

                                                    <!-- ========================= -->
                                                    <!-- SIGNES VITAUX             -->
                                                    <!-- ========================= -->

                                                    <h2 class="section-vitaux" style="margin-top: 2rem;">Signes vitaux
                                                    </h2>

                                                    <div class="patient-info">
                                                        <div class="info-item vital">
                                                            <span class="label">Tension artérielle</span>
                                                            <span class="value">
                                                                <%= patient.getTension() %>
                                                            </span>
                                                        </div>
                                                        <div class="info-item vital">
                                                            <span class="label">Fréquence cardiaque</span>
                                                            <span class="value">
                                                                <%= patient.getFrequenceCardiaque() %> bpm
                                                            </span>
                                                        </div>
                                                        <div class="info-item vital">
                                                            <span class="label">Température</span>
                                                            <span class="value">
                                                                <%= patient.getTemperature() %> °C
                                                            </span>
                                                        </div>
                                                        <div class="info-item vital">
                                                            <span class="label">Fréquence respiratoire</span>
                                                            <span class="value">
                                                                <%= patient.getFrequenceRespiratoire() %> /min
                                                            </span>
                                                        </div>
                                                    </div>

                                                    <% } %>
                            </div>

                            <% if (patient !=null) { %>

                                <!-- ========================= -->
                                <!-- FORMULAIRE CONSULTATION   -->
                                <!-- ========================= -->

                                <div class="card">
                                    <h2 class="section-consultation">Consultation médicale</h2>

                                    <form method="post"
                                        action="${pageContext.request.contextPath}/generaliste/consultation">

                                        <!-- ID du patient -->
                                        <input type="hidden" name="patientId" value="${patient.id}">
                                        <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}">

                                        <!-- MOTIF -->
                                        <div class="form-group">
                                            <label for="motif">Motif de consultation</label>
                                            <input type="text" id="motif" name="motif"
                                                placeholder="Ex : Douleur abdominale, fièvre..."
                                                value="<%= request.getParameter(" motif") !=null ?
                                                request.getParameter("motif") : "" %>"
                                            required>
                                        </div>

                                        <!-- OBSERVATIONS -->
                                        <div class="form-group">
                                            <label for="observations">Observations / Examen clinique</label>
                                            <textarea id="observations" name="observations"
                                                placeholder="Décrire les symptômes et les observations cliniques..."
                                                required><%= request.getParameter("observations") != null
                                                    ? request.getParameter("observations") : "" %></textarea>
                                        </div>

                                        <!-- DIAGNOSTIC -->
                                        <div class="form-group">
                                            <label for="diagnostic">Diagnostic</label>
                                            <textarea id="diagnostic" name="diagnostic"
                                                placeholder="Saisir le diagnostic..." required><%= request.getParameter("diagnostic") != null
                                                    ? request.getParameter("diagnostic") : "" %></textarea>
                                        </div>

                                        <!-- TRAITEMENT -->
                                        <div class="form-group">
                                            <label for="traitement">Traitement prescrit</label>
                                            <textarea id="traitement" name="traitement"
                                                placeholder="Saisir le traitement prescrit..." required><%= request.getParameter("traitement") != null
                                                    ? request.getParameter("traitement") : "" %></textarea>
                                        </div>

                                        <!-- COÛT -->
                                        <div class="cost">Coût de la consultation : 150 DH</div>

                                        <!-- ACTIONS -->
                                        <div class="actions">
                                            <button type="submit" class="btn btn-primary">
                                                ✓ Clôturer la consultation
                                            </button>
                                            <a href="<%= request.getContextPath() %>/generaliste/patients"
                                                class="btn btn-secondary">
                                                Annuler
                                            </a>
                                        </div>

                                    </form>
                                </div>

                                <% } %>

                        </div>
            </body>

            </html>