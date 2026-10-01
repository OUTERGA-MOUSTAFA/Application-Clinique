<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Liste des patients</title>
</head>

<body>

<h1>Liste des patients</h1>

<a href="${pageContext.request.contextPath}/patients/create">
    Ajouter un patient
</a>

<br><br>

<table border="1">

    <thead>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Date naissance</th>
        <th>Tension</th>
        <th>Fréquence cardiaque</th>
        <th>Température</th>
        <th>Statut</th>
    </tr>
    </thead>

    <tbody>

    <%@ page import="java.util.List" %>
    <%@ page import="com.clinique.gestion_clinique.entity.Patient" %>

    <%
        List<Patient> patients =
                (List<Patient>) request.getAttribute("patients");

        for (Patient patient : patients) {
    %>

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
            <%= patient.getStatut() %>
        </td>

    </tr>

    <%
        }
    %>

    </tbody>

</table>

</body>
</html>