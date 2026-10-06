<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouveau patient</title>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f0f4f8;
            color: #2d3748;
            padding: 2rem 1rem;
            min-height: 100vh;
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
        }

        .card {
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
            padding: 2rem;
        }

        h1 {
            color: #2c5282;
            font-size: 1.8rem;
            margin-bottom: 1.5rem;
            padding-bottom: 1rem;
            border-bottom: 3px solid #4299e1;
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }

        h1::before {
            content: "🧑‍⚕️";
            font-size: 2rem;
        }

        .alert-error {
            background: #fed7d7;
            color: #c53030;
            border-left: 4px solid #e53e3e;
            padding: 1rem 1.25rem;
            border-radius: 8px;
            margin-bottom: 1.5rem;
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        /* Grille de formulaire en 2 colonnes */
        .form-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 1.25rem;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group.full-width {
            grid-column: 1 / -1;
        }

        label {
            color: #4a5568;
            font-size: 0.875rem;
            font-weight: 600;
            margin-bottom: 0.5rem;
        }

        input[type="text"],
        input[type="date"],
        input[type="number"] {
            width: 100%;
            padding: 0.75rem 1rem;
            border: 2px solid #e2e8f0;
            border-radius: 8px;
            font-size: 0.95rem;
            color: #2d3748;
            background: #f7fafc;
            transition: all 0.2s ease;
            font-family: inherit;
        }

        input[type="text"]:focus,
        input[type="date"]:focus,
        input[type="number"]:focus {
            outline: none;
            border-color: #4299e1;
            background: #ffffff;
            box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.15);
        }

        input::placeholder {
            color: #a0aec0;
        }

        /* Section signes vitaux */
        .vitals-section {
            grid-column: 1 / -1;
            margin-top: 0.5rem;
            padding-top: 1.25rem;
            border-top: 2px dashed #e2e8f0;
        }

        .vitals-title {
            color: #2c5282;
            font-size: 1rem;
            font-weight: 700;
            margin-bottom: 1rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .vitals-title::before {
            content: "💓";
            font-size: 1.2rem;
        }

        .vitals-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 1.25rem;
        }

        button[type="submit"] {
            grid-column: 1 / -1;
            width: 100%;
            padding: 0.875rem;
            background: linear-gradient(135deg, #2c5282, #4299e1);
            color: #ffffff;
            border: none;
            border-radius: 8px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.2s ease;
            font-family: inherit;
            margin-top: 0.75rem;
            letter-spacing: 0.3px;
        }

        button[type="submit"]:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(66, 153, 225, 0.4);
        }

        button[type="submit"]:active {
            transform: translateY(0);
        }

        .back-link {
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            color: #4299e1;
            text-decoration: none;
            font-weight: 600;
            font-size: 0.9rem;
            padding: 0.625rem 1.25rem;
            border-radius: 8px;
            transition: all 0.2s ease;
            background: #ebf8ff;
            margin-top: 1.5rem;
        }

        .back-link:hover {
            background: #4299e1;
            color: #ffffff;
            transform: translateX(-3px);
        }

        .back-link::before {
            content: "←";
            font-size: 1.1rem;
        }

        @media (max-width: 640px) {
            body { padding: 1rem 0.5rem; }
            .card { padding: 1.25rem; }
            h1 { font-size: 1.4rem; }
            .form-grid, .vitals-grid { grid-template-columns: 1fr; }
        }
    </style>
</head>

<body>
    <div class="container">
        <div class="card">
            <h1>Enregistrer un patient</h1>

            <c:if test="${not empty error}">
                <div class="alert-error">⚠️ ${error}</div>
            </c:if>

            <form method="post"
                  action="${pageContext.request.contextPath}/infirmier/patients/nouveau">

                <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}">

                <div class="form-grid">

                    <!-- Infos identité -->
                    <div class="form-group">
                        <label for="nom">Nom</label>
                        <input type="text" id="nom" name="nom"
                               value="${nom}" placeholder="Dupont" required>
                    </div>

                    <div class="form-group">
                        <label for="prenom">Prénom</label>
                        <input type="text" id="prenom" name="prenom"
                               value="${prenom}" placeholder="Jean" required>
                    </div>

                    <div class="form-group">
                        <label for="dateNaissance">Date de naissance</label>
                        <input type="date" id="dateNaissance" name="dateNaissance"
                               value="${dateNaissance}" required>
                    </div>

                    <div class="form-group">
                        <label for="numSecu">N° sécurité sociale</label>
                        <input type="text" id="numSecu" name="numSecu"
                               value="${numSecu}" placeholder="1 85 12 75 123 456 78" required>
                    </div>

                    <!-- Section signes vitaux -->
                    <div class="vitals-section">
                        <div class="vitals-title">Signes vitaux</div>

                        <div class="vitals-grid">

                            <div class="form-group">
                                <label for="tension">Tension</label>
                                <input type="text" id="tension" name="tension"
                                       value="${tension}" placeholder="120/80" required>
                            </div>

                            <div class="form-group">
                                <label for="frequenceCardiaque">Fréquence cardiaque (bpm)</label>
                                <input type="number" id="frequenceCardiaque" name="frequenceCardiaque"
                                       value="${frequenceCardiaque}" placeholder="75" required>
                            </div>

                            <div class="form-group">
                                <label for="temperature">Température (°C)</label>
                                <input type="number" step="0.1" id="temperature" name="temperature"
                                       value="${temperature}" placeholder="37.0" required>
                            </div>

                            <div class="form-group">
                                <label for="frequenceRespiratoire">Fréquence respiratoire (/min)</label>
                                <input type="number" id="frequenceRespiratoire" name="frequenceRespiratoire"
                                       value="${frequenceRespiratoire}" placeholder="16" required>
                            </div>

                        </div>
                    </div>

                    <button type="submit">Enregistrer le patient</button>

                </div>
            </form>

            <a class="back-link"
               href="${pageContext.request.contextPath}/infirmier/patients">
                Retour à la liste
            </a>
        </div>
    </div>
</body>
</html>