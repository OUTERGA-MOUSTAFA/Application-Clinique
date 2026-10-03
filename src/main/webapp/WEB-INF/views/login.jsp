<%@ page contentType="text/html;charset=UTF-8" %>

    <!DOCTYPE html>
    <html lang="fr">

    <head>
        <meta charset="UTF-8">
        <title>Connexion</title>
    </head>

    <body>

        <h1>Connexion</h1>

        <% String erreur=(String) request.getAttribute("erreur"); %>

            <% if (erreur !=null) { %>
                <p style="color:red;">
                    <%= erreur %>
                </p>
                <% } %>

                    <form method="post" action="${pageContext.request.contextPath}/login">

                        <!-- CSRF -->
                        <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}">

                        <div>
                            <label for="email">Email :</label>

                            <input type="email" id="email" name="email" required>
                        </div>

                        <br>

                        <div>
                            <label for="motDePasse">Mot de passe :</label>

                            <input type="password" id="motDePasse" name="motDePasse" required>
                        </div>

                        <br>

                        <button type="submit">
                            Se connecter
                        </button>

                    </form>

    </body>

    </html>