<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="fr">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Consultation</title>

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

            h2.section-dossier::before {
                content: "📋";
            }

            h2.section-consultation::before {
                content: "✍️";
            }

            .alert-error {
                background: #fed7d7;
                color: #c53030;
                border-left: 4px solid #e53e3e;
                padding: 1rem 1.25rem;
                border-radius: 8px;
                margin-bottom: 1.5rem;
                font-weight: 500;
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
                min-height: 100px;
                line-height: 1.5;
            }

            button[type="submit"] {
                width: 100%;
                padding: 0.875rem;
                background: linear-gradient(135deg, #2c5282, #4299e1);
                color: #ffffff;
                border: none;
                border-radius: 8px;
                font-size: 1rem;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.2s ease;
                font-family: inherit;
                margin-top: 0.5rem;
                letter-spacing: 0.3px;
            }

            button[type="submit"]:hover {
                transform: translateY(-2px);
                box-shadow: 0 8px 20px rgba(66, 153, 225, 0.4);
            }

            button[type="submit"]:active {
                transform: translateY(0);
            }

            .back-link {
                display: inline-flex;
                align-items: center;
                gap: 0.5rem;
                color: #4299e1;
                text-decoration: none;
                font-weight: 600;
                font-size: 0.9rem;
                padding: 0.625rem 1.25rem;
                border-radius: 8px;
                transition: all 0.2s ease;
                background: #ebf8ff;
            }

            .back-link:hover {
                background: #4299e1;
                color: #ffffff;
                transform: translateX(-3px);
            }

            .back-link::before {
                content: "←";
                font-size: 1.1rem;
            }

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
            }
        </style>
    </head>

    <body>
        <div class="container">

            <div class="card">
                <h1>Consultation du patient</h1>

                <%-- Message d'erreur --%>
                    <% String error=(String) request.getAttribute("error"); if (error !=null) { %>
                        <div class="alert-error">⚠️ <%= error %>
                        </div>
                        <% } %>

                            <h2 class="section-dossier">Dossier patient</h2>

                            <div class="patient-info">
                                <div class="info-item">
                                    <span class="label">ID</span>
                                    <span class="value">${patient.id}</span>
                                </div>
                                <div class="info-item">
                                    <span class="label">Nom</span>
                                    <span class="value">${patient.nom}</span>
                                </div>
                                <div class="info-item">
                                    <span class="label">Prénom</span>
                                    <span class="value">${patient.prenom}</span>
                                </div>
                                <div class="info-item">
                                    <span class="label">Date de naissance</span>
                                    <span class="value">${patient.dateNaissance}</span>
                                </div>

                                <div class="info-item vital">
                                    <span class="label">Tension</span>
                                    <span class="value">${patient.tension}</span>
                                </div>
                                <div class="info-item vital">
                                    <span class="label">Fréquence cardiaque</span>
                                    <span class="value">${patient.frequenceCardiaque}</span>
                                </div>
                                <div class="info-item vital">
                                    <span class="label">Température</span>
                                    <span class="value">${patient.temperature}</span>
                                </div>
                                <div class="info-item vital">
                                    <span class="label">Fréquence respiratoire</span>
                                    <span class="value">${patient.frequenceRespiratoire}</span>
                                </div>
                            </div>
            </div>

            <div class="card">
                <h2 class="section-consultation">Consultation</h2>

                <form method="post" action="${pageContext.request.contextPath}/generaliste/consultation">

                    <input type="hidden" name="patientId" value="${patient.id}">

                    <div class="form-group">
                        <label for="motif">Motif</label>
                        <input type="text" id="motif" name="motif" value="${motif}"
                            placeholder="Motif de la consultation" required>
                    </div>

                    <div class="form-group">
                        <label for="observations">Observations</label>
                        <textarea id="observations" name="observations" rows="5"
                            placeholder="Observations cliniques...">${observations}</textarea>
                    </div>

                    <div class="form-group">
                        <label for="diagnostic">Diagnostic</label>
                        <textarea id="diagnostic" name="diagnostic" rows="5" placeholder="Diagnostic médical..."
                            required>${diagnostic}</textarea>
                    </div>

                    <div class="form-group">
                        <label for="traitement">Traitement</label>
                        <textarea id="traitement" name="traitement" rows="5" placeholder="Traitement prescrit..."
                            required>${traitement}</textarea>
                    </div>

                    <button type="submit">
                        Clôturer la consultation
                    </button>

                </form>
            </div>

            <a class="back-link" href="${pageContext.request.contextPath}/generaliste/patients">
                Retour aux patients
            </a>

        </div>
    </body>

    </html>