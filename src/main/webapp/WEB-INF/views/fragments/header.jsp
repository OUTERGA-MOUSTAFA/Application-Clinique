<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    .app-header {
        background: linear-gradient(135deg, #2c5282, #4299e1);
        color: #ffffff;
        padding: 0.875rem 2rem;
        box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
        display: flex;
        align-items: center;
        justify-content: space-between;
        flex-wrap: wrap;
        gap: 1rem;
        position: sticky;
        top: 0;
        z-index: 100;
        margin-bottom: 2rem;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    .app-header .brand {
        display: flex;
        align-items: center;
        gap: 0.625rem;
        font-size: 1.2rem;
        font-weight: 700;
        color: #ffffff;
        text-decoration: none;
        letter-spacing: 0.5px;
    }

    .app-header .brand::before {
        content: "🏥";
        font-size: 1.5rem;
    }

    .app-header .brand:hover {
        opacity: 0.9;
    }

    .app-nav {
        display: flex;
        align-items: center;
        gap: 0.5rem;
        flex: 1;
        margin-left: 2rem;
    }

    .app-nav a {
        color: #ffffff;
        text-decoration: none;
        font-size: 0.875rem;
        font-weight: 600;
        padding: 0.5rem 1rem;
        border-radius: 8px;
        transition: all 0.2s ease;
        opacity: 0.9;
    }

    .app-nav a:hover {
        background: rgba(255, 255, 255, 0.15);
        opacity: 1;
    }

    .app-nav a.active {
        background: rgba(255, 255, 255, 0.25);
        opacity: 1;
    }

    .app-user {
        display: flex;
        align-items: center;
        gap: 0.75rem;
    }

    .user-info {
        display: flex;
        align-items: center;
        gap: 0.5rem;
        font-size: 0.875rem;
        color: #ffffff;
    }

    .user-avatar {
        width: 36px;
        height: 36px;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.2);
        color: #ffffff;
        display: flex;
        align-items: center;
        justify-content: center;
        font-weight: 700;
        font-size: 0.9rem;
        border: 2px solid rgba(255, 255, 255, 0.3);
        text-transform: uppercase;
    }

    .user-name {
        font-weight: 600;
    }

    .user-role {
        font-size: 0.75rem;
        opacity: 0.75;
        display: block;
    }

    /* Bouton Déconnexion */
    .btn-logout {
        display: inline-flex;
        align-items: center;
        gap: 0.5rem;
        padding: 0.5rem 1rem;
        background: rgba(255, 255, 255, 0.15);
        border: 1px solid rgba(255, 255, 255, 0.3);
        color: #ffffff;
        text-decoration: none;
        border-radius: 8px;
        font-size: 0.85rem;
        font-weight: 600;
        cursor: pointer;
        transition: all 0.2s ease;
        font-family: inherit;
        letter-spacing: 0.3px;
    }

    .btn-logout::before {
        content: "🚪";
        font-size: 1rem;
    }

    .btn-logout:hover {
        background: #e53e3e;
        border-color: #e53e3e;
        transform: translateY(-1px);
        box-shadow: 0 4px 12px rgba(229, 62, 62, 0.35);
    }

    .btn-logout:active {
        transform: translateY(0);
    }

    @media (max-width: 768px) {
        .app-header {
            padding: 0.75rem 1rem;
            flex-direction: column;
            align-items: stretch;
        }

        .app-nav {
            margin-left: 0;
            justify-content: center;
            flex-wrap: wrap;
        }

        .app-user {
            justify-content: space-between;
            width: 100%;
        }

        .btn-logout span {
            display: none;
        }

        .btn-logout::before {
            font-size: 1.2rem;
        }
    }
</style>

<header class="app-header">

    <!-- Logo / Marque -->
    <a class="brand" href="${pageContext.request.contextPath}/">
        Clinique Santé+
    </a>

    <!-- Navigation -->
    <nav class="app-nav">
        <c:if test="${sessionScope.role == 'INFIRMIER'}">
            <a href="${pageContext.request.contextPath}/infirmier/patients">Patients</a>
            <a href="${pageContext.request.contextPath}/infirmier/patients/nouveau">Nouveau patient</a>
        </c:if>

        <c:if test="${sessionScope.role == 'GENERALISTE'}">
            <a href="${pageContext.request.contextPath}/generaliste/patients">Patients en attente</a>
        </c:if>
    </nav>

    <!-- Zone utilisateur + bouton Déconnexion -->
    <div class="app-user">

        <c:if test="${not empty sessionScope.utilisateur}">
            <div class="user-info">
                <div class="user-avatar">
                    ${fn:substring(sessionScope.utilisateur.nom, 0, 1)}
                </div>
                <div>
                    <span class="user-name">${sessionScope.utilisateur.nom}</span>
                    <span class="user-role">${sessionScope.role}</span>
                </div>
            </div>
        </c:if>

        <%-- Logout : utilise un lien GET car le LogoutServlet est en doGet --%>
        <a href="${pageContext.request.contextPath}/logout"
           class="btn-logout"
           onclick="return confirm('Voulez-vous vraiment vous déconnecter ?');">
            <span>Déconnexion</span>
        </a>

    </div>

</header>