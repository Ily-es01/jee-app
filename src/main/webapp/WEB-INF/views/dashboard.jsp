
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
</head>

<%@ include file="/WEB-INF/views/header.jsp" %>

    <h1>Bienvenue <c:out value="${user}"/></h1>

    <p>Vous êtes connecté.</p>

    <a href="${pageContext.request.contextPath}/produits">
        Gestion des produits
    </a>

    <br><br>

    <a href="${pageContext.request.contextPath}/logout">
        Se déconnecter
    </a>

</body>
</html>
