<%@ page contentType="text/html;charset=UTF-8" language="java" %>

        <!DOCTYPE html>
        <html>

        <head>
                <meta charset="UTF-8">
                <title>Ajouter un patient</title>
        </head>

        <body>

                <h1>Ajouter un patient</h1>

                <form method="post" action="${pageContext.request.contextPath}/patients/create">
                        <!--  CSRF Token -->
                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}" />

                        <label>Nom :</label>
                        <input type="text" name="nom" required>

                        <br><br>

                        <label>Prénom :</label>
                        <input type="text" name="prenom" required>

                        <br><br>

                        <label>Date de naissance :</label>
                        <input type="date" name="dateNaissance" required>

                        <br><br>

                        <label>Numéro sécurité sociale :</label>
                        <input type="text" name="numSecu">

                        <br><br>

                        <label>Tension :</label>
                        <input type="text" name="tension" placeholder="120/80">

                        <br><br>

                        <label>Fréquence cardiaque :</label>
                        <input type="number" name="frequenceCardiaque" required>

                        <br><br>

                        <label>Température :</label>
                        <input type="number" step="0.1" name="temperature" required>

                        <br><br>

                        <label>Fréquence respiratoire :</label>
                        <input type="number" name="frequenceRespiratoire" required>

                        <br><br>

                        <button type="submit">
                                Enregistrer
                        </button>

                </form>

                <br>

                <a href="${pageContext.request.contextPath}/patients">
                        Retour à la liste
                </a>

        </body>

        </html>