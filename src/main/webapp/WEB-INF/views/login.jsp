<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion</title>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #2c5282 0%, #4299e1 100%);
            color: #2d3748;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 1rem;
        }

        .login-card {
            background: #ffffff;
            border-radius: 16px;
            box-shadow: 0 20px 50px rgba(0, 0, 0, 0.2);
            padding: 2.5rem;
            width: 100%;
            max-width: 420px;
            animation: fadeIn 0.5s ease;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-15px); }
            to   { opacity: 1; transform: translateY(0); }
        }

        .login-header {
            text-align: center;
            margin-bottom: 2rem;
        }

        .login-header .icon {
            font-size: 3rem;
            display: block;
            margin-bottom: 0.75rem;
        }

        h1 {
            color: #2c5282;
            font-size: 1.6rem;
            font-weight: 600;
            margin-bottom: 0.35rem;
        }

        .login-header p {
            color: #718096;
            font-size: 0.9rem;
        }

        .alert-error {
            background: #fed7d7;
            color: #c53030;
            border-left: 4px solid #e53e3e;
            padding: 0.875rem 1rem;
            border-radius: 8px;
            margin-bottom: 1.5rem;
            font-size: 0.9rem;
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .form-group {
            margin-bottom: 1.25rem;
        }

        label {
            display: block;
            color: #4a5568;
            font-size: 0.875rem;
            font-weight: 600;
            margin-bottom: 0.5rem;
        }

        input[type="email"],
        input[type="password"] {
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

        input[type="email"]:focus,
        input[type="password"]:focus {
            outline: none;
            border-color: #4299e1;
            background: #ffffff;
            box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.15);
        }

        input::placeholder {
            color: #a0aec0;
        }

        button[type="submit"] {
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
            margin-top: 0.5rem;
            letter-spacing: 0.3px;
        }

        button[type="submit"]:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(66, 153, 225, 0.4);
        }

        button[type="submit"]:active {
            transform: translateY(0);
            box-shadow: 0 4px 10px rgba(66, 153, 225, 0.3);
        }

        @media (max-width: 480px) {
            .login-card { padding: 1.75rem; }
            h1 { font-size: 1.35rem; }
        }
    </style>
</head>

<body>

    <div class="login-card">

        <div class="login-header">
            <span class="icon">🔐</span>
            <h1>Connexion</h1>
            <p>Accédez à votre espace sécurisé</p>
        </div>

        <% String erreur = (String) request.getAttribute("erreur"); %>
        <% if (erreur != null) { %>
            <div class="alert-error">
                ⚠️ <%= erreur %>
            </div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/login">

            <!-- CSRF -->
            <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}">

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email"
                       id="email"
                       name="email"
                       placeholder="vous@exemple.com"
                       required
                       autofocus>
            </div>

            <div class="form-group">
                <label for="motDePasse">Mot de passe</label>
                <input type="password"
                       id="motDePasse"
                       name="motDePasse"
                       placeholder="••••••••"
                       required>
            </div>

            <button type="submit">
                Se connecter
            </button>

        </form>

    </div>

</body>
</html>