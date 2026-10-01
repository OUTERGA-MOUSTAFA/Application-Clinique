<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>
        Consultation
    </title>

</head>

<body>

<h1>
    Consultation du patient
</h1>

<%-- Message d'erreur --%>

<%
    String error =
            (String) request.getAttribute("error");

    if (error != null) {
%>

<p style="color:red;">
    <%= error %>
</p>

<%
    }
%>

<h2>
    Dossier patient
</h2>

<p>
    <strong>ID:</strong>
    ${patient.id}
</p>

<p>
    <strong>Nom:</strong>
    ${patient.nom}
</p>

<p>
    <strong>Prénom:</strong>
    ${patient.prenom}
</p>

<p>
    <strong>Date de naissance:</strong>
    ${patient.dateNaissance}
</p>

<p>
    <strong>Tension:</strong>
    ${patient.tension}
</p>

<p>
    <strong>Fréquence cardiaque:</strong>
    ${patient.frequenceCardiaque}
</p>

<p>
    <strong>Température:</strong>
    ${patient.temperature}
</p>

<p>
    <strong>Fréquence respiratoire:</strong>
    ${patient.frequenceRespiratoire}
</p>

<hr>

<h2>
    Consultation
</h2>

<form
        method="post"
        action="${pageContext.request.contextPath}/generaliste/consultation"
>

    <input
            type="hidden"
            name="patientId"
            value="${patient.id}"
    >

    <label>
        Motif :
    </label>

    <br>

    <input
            type="text"
            name="motif"
            value="${motif}"
            required
    >

    <br><br>

    <label>
        Observations :
    </label>

    <br>

    <textarea
            name="observations"
            rows="5"
            cols="50"
    >${observations}</textarea>

    <br><br>

    <label>
        Diagnostic :
    </label>

    <br>

    <textarea
            name="diagnostic"
            rows="5"
            cols="50"
            required
    >${diagnostic}</textarea>

    <br><br>

    <label>
        Traitement :
    </label>

    <br>

    <textarea
            name="traitement"
            rows="5"
            cols="50"
            required
    >${traitement}</textarea>

    <br><br>

    <button type="submit">
        Clôturer la consultation
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/generaliste/patients">
    Retour aux patients
</a>

</body>

</html>