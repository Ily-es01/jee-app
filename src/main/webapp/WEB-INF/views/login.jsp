
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion</title>
</head>

<%@ include file="/WEB-INF/views/header.jsp" %>

    <p>Connectez-vous pour continuer.</p>

    <!-- Affichage conditionnel de l'erreur -->
    <c:if test="${not empty erreur}">
        <div class="msg" role="alert">
            <c:out value="${erreur}"/>
        </div>
    </c:if>

    <form id="form"
          action="${pageContext.request.contextPath}/login"
          method="POST">

        <div class="f">
            <input
                id="email"
                name="username"
                type="text"
                placeholder=" "
                value="<c:out value='${username}'/>"
                autocomplete="username"
                required>
            <label for="email">Nom d'utilisateur</label>
        </div>

        <div class="f">
            <input
                id="pw"
                name="password"
                type="password"
                placeholder=" "
                autocomplete="current-password"
                required>
            <label for="pw">Mot de passe</label>
        </div>

        <button class="go" id="go" type="submit">
            <span>Se connecter</span>
        </button>

    </form>

</body>
</html>
