<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="java.util.List" %>
        <%@ page import="com.clinique.gestion_clinique.entity.Patient" %>

            <% List<Patient> patients = (List<Patient>) request.getAttribute("patients");
                    String periode = (String) request.getAttribute("periode");
                    if (periode == null) periode = "aujourd-hui";
                    %>

                    <!DOCTYPE html>
                    <html lang="fr">

                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Patients</title>

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
                                max-width: 1400px;
                                margin: 0 auto;
                            }

                            .card {
                                background: #ffffff;
                                border-radius: 12px;
                                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
                                padding: 2rem;
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
                                content: "📋";
                                font-size: 2rem;
                            }

                            /* ============================ */
                            /* FILTRES                      */
                            /* ============================ */

                            .filters {
                                display: flex;
                                flex-wrap: wrap;
                                gap: 0.625rem;
                                margin-bottom: 1.5rem;
                                padding: 0.875rem;
                                background: #f7fafc;
                                border-radius: 10px;
                                border: 1px solid #e2e8f0;
                            }

                            .filter {
                                padding: 0.625rem 1.25rem;
                                text-decoration: none;
                                border-radius: 8px;
                                background: #ffffff;
                                border: 2px solid #e2e8f0;
                                color: #4a5568;
                                font-size: 0.875rem;
                                font-weight: 600;
                                transition: all 0.2s ease;
                                cursor: pointer;
                            }

                            .filter:hover {
                                border-color: #4299e1;
                                color: #2c5282;
                                transform: translateY(-1px);
                                box-shadow: 0 4px 8px rgba(66, 153, 225, 0.15);
                            }

                            .filter.active {
                                background: linear-gradient(135deg, #2c5282, #4299e1);
                                color: #ffffff;
                                border-color: transparent;
                                box-shadow: 0 4px 12px rgba(66, 153, 225, 0.35);
                            }

                            /* ============================ */
                            /* TABLEAU                      */
                            /* ============================ */

                            .table-wrapper {
                                overflow-x: auto;
                                border-radius: 10px;
                                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
                            }

                            table {
                                width: 100%;
                                border-collapse: collapse;
                                font-size: 0.925rem;
                            }

                            thead {
                                background: linear-gradient(135deg, #2c5282, #4299e1);
                                color: #ffffff;
                            }

                            thead th {
                                padding: 1rem 0.875rem;
                                text-align: left;
                                font-weight: 600;
                                font-size: 0.8rem;
                                text-transform: uppercase;
                                letter-spacing: 0.5px;
                                white-space: nowrap;
                            }

                            tbody tr {
                                border-bottom: 1px solid #e2e8f0;
                                transition: background-color 0.2s ease;
                            }

                            tbody tr:nth-child(even) {
                                background-color: #f7fafc;
                            }

                            tbody tr:hover {
                                background-color: #ebf8ff;
                            }

                            tbody td {
                                padding: 0.875rem;
                                color: #4a5568;
                                vertical-align: middle;
                            }

                            /* Nom + Prénom en gras */
                            tbody td:nth-child(1),
                            tbody td:nth-child(2) {
                                font-weight: 600;
                                color: #2d3748;
                            }

                            /* Heure d'arrivée centrée */
                            tbody td:nth-child(4) {
                                font-weight: 700;
                                color: #2c5282;
                                text-align: center;
                                font-variant-numeric: tabular-nums;
                            }

                            /* Signes vitaux centrés + gras */
                            tbody td:nth-child(5),
                            tbody td:nth-child(6),
                            tbody td:nth-child(7),
                            tbody td:nth-child(8) {
                                font-weight: 600;
                                color: #2d3748;
                                text-align: center;
                            }

                            /* ============================ */
                            /* BADGE DE STATUT              */
                            /* ============================ */

                            .badge {
                                display: inline-block;
                                padding: 0.35rem 0.75rem;
                                border-radius: 20px;
                                font-size: 0.75rem;
                                font-weight: 700;
                                text-transform: uppercase;
                                letter-spacing: 0.5px;
                            }

                            .badge-en-attente {
                                background: #fefcbf;
                                color: #975a16;
                            }

                            .badge-en-consultation {
                                background: #4195c5;
                                color: #a5ccfb;
                            }

                            .badge-termine {
                                background: #2bfe6e;
                                color: #17412d;
                            }

                            /* ============================ */
                            /* EMPTY STATE                  */
                            /* ============================ */

                            .empty {
                                padding: 3rem 2rem;
                                background: #f7fafc;
                                border-radius: 10px;
                                border: 2px dashed #cbd5e0;
                                margin-top: 1rem;
                                text-align: center;
                                color: #718096;
                            }

                            .empty::before {
                                content: "🔍";
                                display: block;
                                font-size: 3rem;
                                margin-bottom: 0.75rem;
                            }

                            .empty p {
                                font-size: 1rem;
                                font-style: italic;
                            }

                            /* ============================ */
                            /* RESPONSIVE                   */
                            /* ============================ */

                            @media (max-width: 768px) {
                                body {
                                    padding: 1rem 0.5rem;
                                }

                                .card {
                                    padding: 1rem;
                                }

                                h1 {
                                    font-size: 1.4rem;
                                }

                                .filters {
                                    padding: 0.625rem;
                                    gap: 0.5rem;
                                }

                                .filter {
                                    padding: 0.5rem 0.875rem;
                                    font-size: 0.8rem;
                                }

                                thead th,
                                tbody td {
                                    padding: 0.625rem 0.5rem;
                                    font-size: 0.8rem;
                                }
                            }
                        </style>
                    </head>

                    <body>
                        <div class="container">
                            <div class="card">

                                <h1>Liste des patients</h1>

                                <!-- ============================ -->
                                <!-- FILTRES                      -->
                                <!-- ============================ -->

                                <div class="filters">
                                    <a class="filter <%= " aujourd-hui".equals(periode) ? "active" : "" %>"
                                        href="<%= request.getContextPath() %>/infirmier/patients?periode=aujourd-hui">
                                            Aujourd'hui
                                    </a>

                                    <a class="filter <%= " hier".equals(periode) ? "active" : "" %>"
                                        href="<%= request.getContextPath() %>/infirmier/patients?periode=hier">
                                            Hier
                                    </a>

                                    <a class="filter <%= " semaine".equals(periode) ? "active" : "" %>"
                                        href="<%= request.getContextPath() %>/infirmier/patients?periode=semaine">
                                            Cette semaine
                                    </a>

                                    <a class="filter <%= " mois".equals(periode) ? "active" : "" %>"
                                        href="<%= request.getContextPath() %>/infirmier/patients?periode=mois">
                                            Ce mois
                                    </a>
                                </div>

                                <!-- ============================ -->
                                <!-- LISTE                        -->
                                <!-- ============================ -->

                                <% if (patients==null || patients.isEmpty()) { %>

                                    <div class="empty">
                                        <p>Aucun patient trouvé pour cette période.</p>
                                    </div>

                                    <% } else { %>

                                        <div class="table-wrapper">
                                            <table>
                                                <thead>
                                                    <tr>
                                                        <th>Nom</th>
                                                        <th>Prénom</th>
                                                        <th>N° sécurité sociale</th>
                                                        <th>Heure d'arrivée</th>
                                                        <th>Tension</th>
                                                        <th>Fréq. cardiaque</th>
                                                        <th>Température</th>
                                                        <th>Fréq. respiratoire</th>
                                                        <th>Statut</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% for (Patient patient : patients) { String
                                                        statut=patient.getStatut(); String badgeClass="badge-en-attente"
                                                        ; if (statut !=null) { String s=statut.toLowerCase(); if
                                                        (s.contains("consultation")) badgeClass="badge-en-consultation"
                                                        ; else if (s.contains("termin")) badgeClass="badge-termine" ; }
                                                        %>
                                                        <tr>
                                                            <td>
                                                                <%= patient.getNom() %>
                                                            </td>
                                                            <td>
                                                                <%= patient.getPrenom() %>
                                                            </td>
                                                            <td>
                                                                <%= patient.getNumSecu() %>
                                                            </td>
                                                            <td>
                                                                <%= patient.getHeureArrivee() %>
                                                            </td>
                                                            <td>
                                                                <%= patient.getTension() %>
                                                            </td>
                                                            <td>
                                                                <%= patient.getFrequenceCardiaque() %> bpm
                                                            </td>
                                                            <td>
                                                                <%= patient.getTemperature() %> °C
                                                            </td>
                                                            <td>
                                                                <%= patient.getFrequenceRespiratoire() %> /min
                                                            </td>
                                                            <td>
                                                                <span class="badge <%= badgeClass %>">
                                                                    <%= patient.getStatut() %>
                                                                </span>
                                                            </td>
                                                        </tr>
                                                        <% } %>
                                                </tbody>
                                            </table>
                                        </div>

                                        <% } %>

                            </div>
                        </div>
                    </body>

                    </html>