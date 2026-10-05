<%@ page contentType="text/html;charset=UTF-8" %>
    <!DOCTYPE html>
    <html lang="fr">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Connexion — Clinique</title>

        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                min-height: 100vh;
                background: #ffffff;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 1.5rem;
            }

            .login-wrapper {
                display: grid;
                grid-template-columns: 1fr 1fr;
                width: 100%;
                max-width: 1200px;
                min-height: 650px;
                background: #ffffff;
                border-radius: 24px;
                overflow: hidden;
                box-shadow: 0 20px 60px rgba(0, 0, 0, 0.12);
            }

            /* ==========================================
           PANNEAU GAUCHE — Bleu avec illustration
           ========================================== */

            .left-panel {
                background: #0d8bf0;
                color: #ffffff;
                padding: 3rem 2.5rem;
                display: flex;
                flex-direction: column;
                position: relative;
                overflow: hidden;
            }

            .brand {
                font-size: 1.5rem;
                font-weight: 700;
                letter-spacing: 0.5px;
                margin-bottom: 3rem;
            }

            .brand span {
                color: #ffffff;
                opacity: 0.95;
            }

            .left-content h2 {
                font-size: 2rem;
                font-weight: 700;
                line-height: 1.2;
                margin-bottom: 0.5rem;
            }

            .left-content h3 {
                font-size: 1.35rem;
                font-weight: 400;
                line-height: 1.3;
                margin-bottom: 1.5rem;
                opacity: 0.95;
            }

            .left-content p {
                font-size: 0.9rem;
                line-height: 1.6;
                opacity: 0.85;
                max-width: 380px;
            }

            /* Illustration / icône centrale */
            .illustration {
                flex: 1;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 7rem;
                opacity: 0.9;
                margin: 1rem 0;
                animation: float 3s ease-in-out infinite;
            }

            @keyframes float {

                0%,
                100% {
                    transform: translateY(0);
                }

                50% {
                    transform: translateY(-10px);
                }
            }

            /* Section "Login as" */
            .login-as {
                margin-top: auto;
            }

            .login-as-title {
                font-size: 0.95rem;
                font-weight: 600;
                margin-bottom: 1rem;
                opacity: 0.95;
            }

            .user-cards {
                display: flex;
                gap: 1rem;
            }

            .user-card {
                background: rgba(255, 255, 255, 0.15);
                border: 1px solid rgba(255, 255, 255, 0.2);
                border-radius: 12px;
                padding: 1rem;
                text-align: center;
                cursor: pointer;
                transition: all 0.2s ease;
                backdrop-filter: blur(10px);
                flex: 1;
                max-width: 140px;
            }

            .user-card:hover {
                background: rgba(255, 255, 255, 0.25);
                transform: translateY(-3px);
            }

            .user-avatar {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                background: #ffffff;
                color: #0d8bf0;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 1.5rem;
                font-weight: 700;
                margin: 0 auto 0.5rem;
            }

            .user-name {
                font-size: 0.8rem;
                font-weight: 600;
                margin-bottom: 0.15rem;
            }

            .user-status {
                font-size: 0.7rem;
                opacity: 0.75;
            }

            /* ==========================================
           PANNEAU DROIT — Formulaire
           ========================================== */

            .right-panel {
                padding: 3rem 3rem;
                display: flex;
                flex-direction: column;
                justify-content: center;
                background: #ffffff;
            }

            .top-bar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 2rem;
                font-size: 0.85rem;
            }

            .welcome-text {
                color: #4a5568;
            }

            .welcome-text strong {
                color: #0d8bf0;
                font-weight: 700;
            }

            .signup-link {
                color: #718096;
                font-size: 0.8rem;
            }

            .signup-link a {
                color: #0d8bf0;
                text-decoration: none;
                font-weight: 600;
            }

            .signup-link a:hover {
                text-decoration: underline;
            }

            .right-panel h1 {
                font-size: 2.5rem;
                font-weight: 700;
                color: #1a202c;
                margin-bottom: 2rem;
                letter-spacing: -0.5px;
            }

            /* Boutons sociaux */
            .social-buttons {
                display: grid;
                grid-template-columns: 1fr auto auto;
                gap: 0.75rem;
                margin-bottom: 2rem;
            }

            .social-btn {
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 0.5rem;
                padding: 0.75rem 1rem;
                border: 1px solid #e2e8f0;
                border-radius: 8px;
                background: #f7fafc;
                cursor: pointer;
                transition: all 0.2s ease;
                font-size: 0.875rem;
                font-weight: 600;
                color: #4a5568;
                text-decoration: none;
                font-family: inherit;
            }

            .social-btn:hover {
                border-color: #cbd5e0;
                background: #ffffff;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
            }

            .social-btn-icon {
                width: 38px;
                padding: 0.75rem 0;
            }

            .social-google {
                color: #4a5568;
            }

            .social-google svg {
                width: 18px;
                height: 18px;
            }

            /* Message d'erreur */
            .alert-error {
                background: #fed7d7;
                color: #c53030;
                border-left: 4px solid #e53e3e;
                padding: 0.75rem 1rem;
                border-radius: 6px;
                margin-bottom: 1.5rem;
                font-size: 0.875rem;
                font-weight: 500;
                display: flex;
                align-items: center;
                gap: 0.5rem;
            }

            /* Formulaire */
            .form-group {
                margin-bottom: 1.25rem;
            }

            label {
                display: block;
                color: #2d3748;
                font-size: 0.875rem;
                font-weight: 600;
                margin-bottom: 0.5rem;
            }

            input[type="email"],
            input[type="password"] {
                width: 100%;
                padding: 0.875rem 1rem;
                border: 1.5px solid #cbd5e0;
                border-radius: 8px;
                font-size: 0.95rem;
                color: #2d3748;
                background: #ffffff;
                transition: all 0.2s ease;
                font-family: inherit;
            }

            input[type="email"]:focus,
            input[type="password"]:focus {
                outline: none;
                border-color: #0d8bf0;
                box-shadow: 0 0 0 3px rgba(13, 139, 240, 0.15);
            }

            input::placeholder {
                color: #a0aec0;
            }

            .forgot-link {
                display: block;
                text-align: right;
                color: #0d8bf0;
                text-decoration: none;
                font-size: 0.8rem;
                font-weight: 600;
                margin-top: -0.5rem;
                margin-bottom: 1.5rem;
            }

            .forgot-link:hover {
                text-decoration: underline;
            }

            button[type="submit"] {
                width: 100%;
                padding: 0.95rem;
                background: #0d8bf0;
                color: #ffffff;
                border: none;
                border-radius: 8px;
                font-size: 1rem;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.2s ease;
                font-family: inherit;
                letter-spacing: 0.3px;
            }

            button[type="submit"]:hover {
                background: #0b7ad4;
                transform: translateY(-1px);
                box-shadow: 0 8px 20px rgba(13, 139, 240, 0.35);
            }

            button[type="submit"]:active {
                transform: translateY(0);
            }

            /* ==========================================
           RESPONSIVE
           ========================================== */

            @media (max-width: 900px) {
                .login-wrapper {
                    grid-template-columns: 1fr;
                    max-width: 500px;
                }

                .left-panel {
                    padding: 2rem;
                    min-height: 280px;
                }

                .illustration {
                    font-size: 4rem;
                    margin: 0.5rem 0;
                }

                .login-as {
                    display: none;
                }

                .left-content h2 {
                    font-size: 1.5rem;
                }

                .left-content h3 {
                    font-size: 1.1rem;
                }

                .left-content p {
                    display: none;
                }

                .right-panel {
                    padding: 2rem;
                }

                .right-panel h1 {
                    font-size: 2rem;
                }

                .social-buttons {
                    grid-template-columns: 1fr 1fr 1fr;
                }
            }

            @media (max-width: 480px) {
                body {
                    padding: 0.5rem;
                }

                .right-panel {
                    padding: 1.5rem;
                }

                .right-panel h1 {
                    font-size: 1.75rem;
                }
            }
        </style>
    </head>

    <body>

        <div class="login-wrapper">

            <!-- ==========================================
             PANNEAU GAUCHE — Bleu
             ========================================== -->
            <div class="left-panel">
                <div class="brand">Clinique <span>Santé+</span></div>

                <div class="left-content">
                    <h2>Bienvenue sur</h2>
                    <h3>Votre espace médical sécurisé</h3>
                    <p>
                        Gérez vos consultations, patients et dossiers médicaux
                        en toute simplicité. Une plateforme pensée pour les
                        professionnels de santé.
                    </p>
                </div>

                <div class="illustration">🩺</div>

                <div class="login-as">
                    <div class="login-as-title">Connexion rapide</div>
                    <div class="user-cards">
                        <div class="user-card">
                            <div class="user-avatar">M</div>
                            <div class="user-name">Dr. Ahmed</div>
                            <div class="user-status">Médecin</div>
                        </div>
                        <div class="user-card">
                            <div class="user-avatar">S</div>
                            <div class="user-name">Mme. Haja</div>
                            <div class="user-status">Infirmière</div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- ==========================================
             PANNEAU DROIT — Formulaire
             ========================================== -->
            <div class="right-panel">

                <div class="top-bar">
                    <div class="welcome-text">
                        Bienvenue sur <strong>CLINIQUE</strong>
                    </div>
                    <div class="signup-link">
                        Pas de compte ? <a href="#">S'inscrire</a>
                    </div>
                </div>

                <h1>Connexion</h1>

                <!-- Boutons sociaux -->
                <div class="social-buttons">
                    <a href="#" class="social-btn social-google">
                        <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                            <path fill="#4285F4"
                                d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" />
                            <path fill="#34A853"
                                d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" />
                            <path fill="#FBBC05"
                                d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z" />
                            <path fill="#EA4335"
                                d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z" />
                        </svg>
                        Continuer avec Google
                    </a>
                    <a href="#" class="social-btn social-btn-icon" title="Facebook">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="#1877F2">
                            <path
                                d="M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385H7.078v-3.47h3.047V9.43c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953H15.83c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.47h-2.796v8.385C19.612 23.027 24 18.062 24 12.073z" />
                        </svg>
                    </a>
                    <a href="#" class="social-btn social-btn-icon" title="X">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="#000000">
                            <path
                                d="M18.244 2.25h3.308l-7.227 8.26 8.502 11.24H16.17l-5.214-6.817L4.99 21.75H1.68l7.73-8.835L1.254 2.25H8.08l4.713 6.231zm-1.161 17.52h1.833L7.084 4.126H5.117z" />
                        </svg>
                    </a>
                </div>

                <!-- Message d'erreur -->
                <% String erreur=(String) request.getAttribute("erreur"); %>
                    <% if (erreur !=null) { %>
                        <div class="alert-error">
                            ⚠️ <%= erreur %>
                        </div>
                        <% } %>

                            <!-- Formulaire -->
                            <form method="post" action="${pageContext.request.contextPath}/login">

                                <!-- CSRF -->
                                <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}">

                                <div class="form-group">
                                    <label for="email">Entrez votre email</label>
                                    <input type="email" id="email" name="email" placeholder="vous@exemple.com" required
                                        autofocus>
                                </div>

                                <div class="form-group">
                                    <label for="motDePasse">Entrez votre mot de passe</label>
                                    <input type="password" id="motDePasse" name="motDePasse" placeholder="••••••••"
                                        required>
                                </div>

                                <a href="#" class="forgot-link">Mot de passe oublié ?</a>

                                <button type="submit">Se connecter</button>

                            </form>

            </div>

        </div>

    </body>

    </html>