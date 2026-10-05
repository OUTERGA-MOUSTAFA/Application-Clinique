<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Patients — Généraliste</title>

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
                    content: "🩺";
                    font-size: 2rem;
                }

                /* Filtres de statut */
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
                    display: inline-flex;
                    align-items: center;
                    gap: 0.5rem;
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

                .filter .count {
                    background: rgba(0, 0, 0, 0.1);
                    padding: 0.125rem 0.5rem;
                    border-radius: 12px;
                    font-size: 0.75rem;
                    font-weight: 700;
                }

                .filter.active .count {
                    background: rgba(255, 255, 255, 0.25);
                }

                /* Tableau */
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

                tbody td:first-child {
                    font-weight: 600;
                    color: #2c5282;
                }

                tbody td:nth-child(5),
                tbody td:nth-child(6),
                tbody td:nth-child(7),
                tbody td:nth-child(8) {
                    font-weight: 600;
                    color: #2d3748;
                    text-align: center;
                }

                /* Badge de statut */
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

                .badge-en-cours {
                    background: #bee3f8;
                    color: #2c5282;
                }

                .badge-terminee {
                    background: #c6f6d5;
                    color: #22543d;
                }

                .btn-consulter {
                    display: inline-block;
                    padding: 0.5rem 1rem;
                    background: linear-gradient(135deg, #2c5282, #4299e1);
                    color: #ffffff;
                    text-decoration: none;
                    border-radius: 6px;
                    font-size: 0.85rem;
                    font-weight: 600;
                    transition: all 0.2s ease;
                    white-space: nowrap;
                }

                .btn-consulter:hover {
                    transform: translateY(-1px);
                    box-shadow: 0 4px 8px rgba(66, 153, 225, 0.35);
                }

                .empty-state {
                    text-align: center;
                    padding: 3rem;
                    color: #718096;
                    font-style: italic;
                }

                .empty-state::before {
                    content: "🔍";
                    display: block;
                    font-size: 3rem;
                    margin-bottom: 0.75rem;
                }

                @media (max-width: 768px) {
                    body {
                        padding: 1rem 0.5rem;
                    }

                    .container {
                        padding: 1rem;
                    }

                    h1 {
                        font-size: 1.4rem;
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

            <%@ include file="/WEB-INF/views/fragments/header.jsp" %>

                <div class="container">
                    <h1>Patients — Généraliste</h1>

                    <!-- ========================================== -->
                    <!-- FILTRES PAR STATUT                          -->
                    <!-- ========================================== -->
                    <div class="filters">
                        <a class="filter ${statut == 'EN_ATTENTE' ? 'active' : ''}"
                            href="${pageContext.request.contextPath}/generaliste/patients?statut=EN_ATTENTE">
                            En attente
                        </a>

                        <a class="filter ${statut == 'EN_COURS' ? 'active' : ''}"
                            href="${pageContext.request.contextPath}/generaliste/patients?statut=EN_COURS">
                            En cours
                        </a>

                        <a class="filter ${statut == 'TERMINEE' ? 'active' : ''}"
                            href="${pageContext.request.contextPath}/generaliste/patients?statut=TERMINEE">
                            Terminées
                        </a>
                    </div>

                    <!-- ========================================== -->
                    <!-- TABLEAU                                     -->
                    <!-- ========================================== -->
                    <div class="table-wrapper">
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nom</th>
                                    <th>Prénom</th>
                                    <th>Date naissance</th>
                                    <th>Tension</th>
                                    <th>Fréq. cardiaque</th>
                                    <th>Température</th>
                                    <th>Fréq. respiratoire</th>
                                    <th>Statut</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${empty patients}">
                                        <tr>
                                            <td colspan="10" class="empty-state">
                                                Aucun patient pour ce statut.
                                            </td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="patient" items="${patients}">
                                            <tr>
                                                <td>${patient.id}</td>
                                                <td>${patient.nom}</td>
                                                <td>${patient.prenom}</td>
                                                <td>${patient.dateNaissance}</td>
                                                <td>${patient.tension}</td>
                                                <td>${patient.frequenceCardiaque} bpm</td>
                                                <td>${patient.temperature} °C</td>
                                                <td>${patient.frequenceRespiratoire} /min</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${patient.statut == 'EN_ATTENTE'}">
                                                            <span class="badge badge-en-attente">En attente</span>
                                                        </c:when>
                                                        <c:when test="${patient.statut == 'EN_COURS'}">
                                                            <span class="badge badge-en-cours">En cours</span>
                                                        </c:when>
                                                        <c:when test="${patient.statut == 'TERMINEE'}">
                                                            <span class="badge badge-terminee">Terminée</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span
                                                                class="badge badge-en-attente">${patient.statut}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:if test="${patient.statut != 'TERMINEE'}">
                                                        <a class="btn-consulter"
                                                            href="${pageContext.request.contextPath}/generaliste/consultation?patientId=${patient.id}">
                                                            Consulter
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${patient.statut == 'TERMINEE'}">
                                                        <span style="color: #718096; font-size: 0.85rem;">—</span>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>

                </div>
        </body>

        </html>