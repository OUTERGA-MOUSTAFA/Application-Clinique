<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib
        prefix="c"
        uri="jakarta.tags.core"
%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>
        Patients en attente
    </title>

</head>

<body>

<h1>
    Patients en attente
</h1>

<c:if test="${not empty param.error}">

    <p style="color:red;">
        ${param.error}
    </p>

</c:if>

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
        <th>Fréquence respiratoire</th>
        <th>Action</th>

    </tr>

    </thead>

    <tbody>

    <c:forEach
            var="patient"
            items="${patients}"
    >

        <tr>

            <td>
                ${patient.id}
            </td>

            <td>
                ${patient.nom}
            </td>

            <td>
                ${patient.prenom}
            </td>

            <td>
                ${patient.dateNaissance}
            </td>

            <td>
                ${patient.tension}
            </td>

            <td>
                ${patient.frequenceCardiaque}
            </td>

            <td>
                ${patient.temperature}
            </td>

            <td>
                ${patient.frequenceRespiratoire}
            </td>

            <td>

                <a href="${pageContext.request.contextPath}/generaliste/consultation?patientId=${patient.id}">
                    Consulter
                </a>

            </td>

        </tr>

    </c:forEach>

    </tbody>

</table>

</body>

</html>