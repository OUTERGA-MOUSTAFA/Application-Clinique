<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Page d'authentification - Gestion clinique</title>
</head>
<body>
    <h2>Login</h2>
    <form action="login" method="post">
        <!--  CSRF Token -->
        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}"/>
        
        <label>Nom: </label>
        <input type="text" name="username" required/><br/><br/>
        
        <label>password: </label>
        <input type="password" name="password" required/><br/><br/>
        
        <button type="submit">Entre</button>
    </form>
</body>
</html>