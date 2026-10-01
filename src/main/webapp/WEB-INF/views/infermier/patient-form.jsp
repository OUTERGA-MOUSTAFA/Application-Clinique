<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Page de register les patients - Gestion clinique</title>
</head>
<body>
    <h2>Register patients</h2>
    
    <form method="post" action="/patients/create">
        <input type="hidden"
       name="csrfToken"
       value="${sessionScope.csrfToken}">
    <input name="nom">
    <input name="prenom">
    <input name="dateNaissance">

    <input name="tension">
    <input name="frequenceCardiaque">
    <input name="temperature">
    <input name="frequenceRespiratoire">

    <button>Enregistrer</button>

</form>