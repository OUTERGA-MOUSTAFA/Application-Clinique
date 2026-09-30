<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
</head>
<body>
    <main>
        <h1>Connexion</h1>
        <c:if test="${not empty erreur}">
            <p class="erreur">${erreur}</p>
        </c:if>
        <form method="post" action="${pageContext.request.contextPath}/login">
            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">

            <label for="email">E-mail</label>
            <input type="email" id="email" name="email" required>

            <label for="motDePasse">Mot de passe</label>
            <input type="password" id="motDePasse" name="motDePasse" required>

            <button type="submit">Se connecter</button>
        </form>
    </main>
</body>
</html>