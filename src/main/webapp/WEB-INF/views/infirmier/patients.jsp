<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>

<html lang="fr">

<head>

    <meta charset="UTF-8">

    <title>Patients du jour</title>

</head>

<body>

<h1>Patients du jour</h1>

<a href="${pageContext.request.contextPath}/infirmier/patients/nouveau">
    Nouveau patient
</a>

<br>
<br>

<table border="1">

    <thead>

    <tr>
        <th>Nom</th>
        <th>Prénom</th>
        <th>N° sécurité sociale</th>
        <th>Heure arrivée</th>
        <th>Tension</th>
        <th>Fréquence cardiaque</th>
        <th>Température</th>
        <th>Fréquence respiratoire</th>
    </tr>

    </thead>

    <tbody>

    <c:forEach var="patient" items="${patients}">

        <tr>

            <td>${patient.nom}</td>

            <td>${patient.prenom}</td>

            <td>${patient.numSecu}</td>

            <td>${patient.heureArrivee}</td>

            <td>${patient.tension}</td>

            <td>${patient.frequenceCardiaque}</td>

            <td>${patient.temperature}</td>

            <td>${patient.frequenceRespiratoire}</td>

        </tr>

    </c:forEach>

    </tbody>

</table>

</body>

</html>