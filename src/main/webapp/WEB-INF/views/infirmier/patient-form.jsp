<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <!DOCTYPE html>

        <html lang="fr">

        <head>

            <meta charset="UTF-8">

            <title>Nouveau patient</title>

        </head>

        <body>

            <h1>Enregistrer un patient</h1>

            <c:if test="${not empty error}">

                <p style="color:red">
                    ${error}
                </p>

            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/infirmier/patients/nouveau">

                <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}">
                <div>

                    <label>Nom :</label>

                    <input type="text" name="nom" value="${nom}" required>

                </div>

                <br>

                <div>

                    <label>Prénom :</label>

                    <input type="text" name="prenom" value="${prenom}" required>

                </div>

                <br>

                <div>

                    <label>Date de naissance :</label>

                    <input type="date" name="dateNaissance" value="${dateNaissance}" required>

                </div>

                <br>

                <div>

                    <label>N° sécurité sociale :</label>

                    <input type="text" name="numSecu" value="${numSecu}" required>

                </div>

                <br>

                <div>

                    <label>Tension :</label>

                    <input type="text" name="tension" value="${tension}" placeholder="120/80" required>

                </div>

                <br>

                <div>

                    <label>Fréquence cardiaque :</label>

                    <input type="number" name="frequenceCardiaque" value="${frequenceCardiaque}" required>

                </div>

                <br>

                <div>

                    <label>Température :</label>

                    <input type="number" step="0.1" name="temperature" value="${temperature}" required>

                </div>

                <br>

                <div>

                    <label>Fréquence respiratoire :</label>

                    <input type="number" name="frequenceRespiratoire" value="${frequenceRespiratoire}" required>

                </div>

                <br>

                <button type="submit">
                    Enregistrer
                </button>

            </form>

            <br>

            <a href="${pageContext.request.contextPath}/infirmier/patients">
                Retour à la liste
            </a>

        </body>

        </html>