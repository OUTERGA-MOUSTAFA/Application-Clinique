<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Erreur</title>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #2c5282, #4299e1);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 2rem 1rem;
        }

        .error-card {
            background: #ffffff;
            border-radius: 16px;
            box-shadow: 0 20px 50px rgba(0, 0, 0, 0.2);
            padding: 3rem 2.5rem;
            max-width: 500px;
            width: 100%;
            text-align: center;
            animation: fadeIn 0.4s ease;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-15px); }
            to   { opacity: 1; transform: translateY(0); }
        }

        .error-icon {
            font-size: 4rem;
            margin-bottom: 1rem;
        }

        h1 {
            color: #2c5282;
            font-size: 1.6rem;
            margin-bottom: 0.75rem;
        }

        .error-code {
            display: inline-block;
            background: #ebf8ff;
            color: #2c5282;
            font-size: 0.85rem;
            font-weight: 700;
            padding: 0.35rem 0.875rem;
            border-radius: 20px;
            margin-bottom: 1.25rem;
            letter-spacing: 0.5px;
        }

        .error-message {
            color: #4a5568;
            font-size: 0.95rem;
            line-height: 1.6;
            margin-bottom: 2rem;
        }

        .error-details {
            background: #f7fafc;
            border-left: 4px solid #e53e3e;
            padding: 0.875rem 1rem;
            border-radius: 6px;
            text-align: left;
            font-size: 0.8rem;
            color: #4a5568;
            font-family: 'Courier New', monospace;
            margin-bottom: 2rem;
            overflow-x: auto;
            white-space: pre-wrap;
            word-break: break-word;
        }

        .actions {
            display: flex;
            gap: 0.75rem;
            justify-content: center;
            flex-wrap: wrap;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            padding: 0.75rem 1.5rem;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
            font-size: 0.9rem;
            transition: all 0.2s ease;
            border: none;
            cursor: pointer;
            font-family: inherit;
        }

        .btn-primary {
            background: linear-gradient(135deg, #2c5282, #4299e1);
            color: #ffffff;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(66, 153, 225, 0.4);
        }

        .btn-secondary {
            background: #ffffff;
            color: #4a5568;
            border: 2px solid #e2e8f0;
        }

        .btn-secondary:hover {
            border-color: #cbd5e0;
            background: #f7fafc;
        }

        @media (max-width: 480px) {
            .error-card { padding: 2rem 1.5rem; }
            h1 { font-size: 1.35rem; }
            .error-icon { font-size: 3rem; }
        }
    </style>
</head>

<body>

    <div class="error-card">

        <div class="error-icon">
            <%
                Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
                if (statusCode != null && statusCode == 404) {
            %>
                🔍
            <% } else if (statusCode != null && statusCode == 403) { %>
                🚫
            <% } else if (statusCode != null && statusCode == 500) { %>
                ⚠️
            <% } else { %>
                ❌
            <% } %>
        </div>

        <h1>
            <%
                if (statusCode != null && statusCode == 404) {
                    out.print("Page introuvable");
                } else if (statusCode != null && statusCode == 403) {
                    out.print("Accès refusé");
                } else if (statusCode != null && statusCode == 500) {
                    out.print("Erreur interne du serveur");
                } else {
                    out.print("Une erreur est survenue");
                }
            %>
        </h1>

        <% if (statusCode != null) { %>
            <span class="error-code">ERREUR <%= statusCode %></span>
        <% } %>

        <p class="error-message">
            <%
                String errorMessage = (String) request.getAttribute("jakarta.servlet.error.message");
                if (statusCode != null && statusCode == 404) {
                    out.print("La page que vous recherchez n'existe pas ou a été déplacée.");
                } else if (statusCode != null && statusCode == 403) {
                    out.print("Vous n'avez pas les permissions nécessaires pour accéder à cette ressource.");
                } else if (errorMessage != null && !errorMessage.isEmpty()) {
                    out.print(errorMessage);
                } else {
                    out.print("Une erreur inattendue s'est produite. Veuillez réessayer.");
                }
            %>
        </p>

        <%-- Détails techniques (visible uniquement pour les admins ou en dev) --%>
        <%
            Throwable exception = (Throwable) request.getAttribute("jakarta.servlet.error.exception");
            if (exception != null && Boolean.TRUE.equals(request.getAttribute("showErrorDetails"))) {
        %>
            <div class="error-details"><%= exception.getMessage() %></div>
        <% } %>

        <div class="actions">
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                🏠 Retour à l'accueil
            </a>
            <button onclick="history.back()" class="btn btn-secondary">
                ← Retour
            </button>
        </div>

    </div>

</body>
</html>