<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="java.util.List" %>
        <%@ page import="com.clinique.gestion_clinique.entity.Patient" %>
            <!DOCTYPE html>
            <html lang="fr">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Liste des patients</title>

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

                    .header {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        flex-wrap: wrap;
                        gap: 1rem;
                        margin-bottom: 1.5rem;
                        padding-bottom: 1rem;
                        border-bottom: 3px solid #4299e1;
                    }

                    h1 {
                        color: #2c5282;
                        font-size: 1.8rem;
                        display: flex;
                        align-items: center;
                        gap: 0.75rem;
                    }

                    h1::before {
                        content: "📋";
                        font-size: 2rem;
                    }

                    .btn-add {
                        display: inline-flex;
                        align-items: center;
                        gap: 0.5rem;
                        padding: 0.625rem 1.25rem;
                        background: linear-gradient(135deg, #2c5282, #4299e1);
                        color: #ffffff;
                        text-decoration: none;
                        border-radius: 8px;
                        font-size: 0.9rem;
                        font-weight: 600;
                        transition: all 0.2s ease;
                        letter-spacing: 0.3px;
                    }

                    .btn-add::before {
                        content: "➕";
                        font-size: 1rem;
                    }

                    .btn-add:hover {
                        transform: translateY(-2px);
                        box-shadow: 0 8px 20px rgba(66, 153, 225, 0.4);
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

                    tbody td:nth-child(5),
                    tbody td:nth-child(6),
                    tbody td:nth-child(7) {
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

                    .badge-en-consultation {
                        background: #bee3f8;
                        color: #2c5282;
                    }

                    .badge-termine {
                        background: #c6f6d5;
                        color: #22543d;
                    }

                    .empty-state {
                        text-align: center;
                        padding: 3rem;
                        color: #718096;
                        font-style: italic;
                    }

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

                        .header {
                            flex-direction: column;
                            align-items: flex-start;
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

                        <div class="header">
                            <h1>Liste des patients</h1>
                            <a class="btn-add" href="${pageContext.request.contextPath}/patients/create">
                                Ajouter un patient
                            </a>
                        </div>

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
                                        <th>Statut</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% List<Patient> patients =
                                        (List<Patient>) request.getAttribute("patients");

                                            if (patients == null || patients.isEmpty()) {
                                            %>
                                            <tr>
                                                <td colspan="8" class="empty-state">
                                                    Aucun patient enregistré pour le moment.
                                                </td>
                                            </tr>
                                            <% } else { for (Patient patient : patients) { String
                                                statut=patient.getStatut(); String badgeClass="badge-en-attente" ; if
                                                (statut !=null) { String s=statut.toLowerCase(); if
                                                (s.contains("consultation")) badgeClass="badge-en-consultation" ; else
                                                if (s.contains("termin")) badgeClass="badge-termine" ; } %>
                                                <tr>
                                                    <td>
                                                        <%= patient.getId() %>
                                                    </td>
                                                    <td>
                                                        <%= patient.getNom() %>
                                                    </td>
                                                    <td>
                                                        <%= patient.getPrenom() %>
                                                    </td>
                                                    <td>
                                                        <%= patient.getDateNaissance() %>
                                                    </td>
                                                    <td>
                                                        <%= patient.getTension() %>
                                                    </td>
                                                    <td>
                                                        <%= patient.getFrequenceCardiaque() %>
                                                    </td>
                                                    <td>
                                                        <%= patient.getTemperature() %>
                                                    </td>
                                                    <td>
                                                        <span class="badge <%= badgeClass %>">
                                                            <%= patient.getStatut() %>
                                                        </span>
                                                    </td>
                                                </tr>
                                                <% } } %>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
            </body>

            </html>