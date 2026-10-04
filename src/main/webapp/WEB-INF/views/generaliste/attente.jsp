<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Patients en attente</title>

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
                    padding: 2rem;
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
                    
                    font-size: 2rem;
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
                    font-size: 0.85rem;
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

                /* Colonnes vitales — mise en valeur */
                tbody td:nth-child(5),
                tbody td:nth-child(6),
                tbody td:nth-child(7),
                tbody td:nth-child(8) {
                    font-weight: 600;
                    color: #2d3748;
                    text-align: center;
                }

                .btn-consulter {
                    display: inline-block;
                    padding: 0.5rem 1rem;
                    background: #4299e1;
                    color: #ffffff;
                    text-decoration: none;
                    border-radius: 6px;
                    font-size: 0.85rem;
                    font-weight: 600;
                    transition: all 0.2s ease;
                    white-space: nowrap;
                }

                .btn-consulter:hover {
                    background: #2c5282;
                    transform: translateY(-1px);
                    box-shadow: 0 4px 8px rgba(66, 153, 225, 0.3);
                }

                .empty-state {
                    text-align: center;
                    padding: 3rem;
                    color: #718096;
                    font-style: italic;
                }

                @media (max-width: 768px) {
                    body {
                        padding: 1rem;
                    }

                    .container {
                        padding: 1rem;
                    }

                    h1 {
                        font-size: 1.4rem;
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
                <h1>Patients en attente</h1>

                <c:if test="${not empty param.error}">
                    <div class="alert-error">
                        ⚠️ ${param.error}
                    </div>
                </c:if>

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
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty patients}">
                                    <tr>
                                        <td colspan="9" class="empty-state">
                                            Aucun patient en attente pour le moment.
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
                                            <td>${patient.frequenceCardiaque}</td>
                                            <td>${patient.temperature}</td>
                                            <td>${patient.frequenceRespiratoire}</td>
                                            <td>
                                                <a class="btn-consulter"
                                                    href="${pageContext.request.contextPath}/generaliste/consultation?patientId=${patient.id}">
                                                    Consulter
                                                </a>
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