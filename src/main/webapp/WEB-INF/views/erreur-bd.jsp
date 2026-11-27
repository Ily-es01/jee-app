<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<h1>Erreur de base de données</h1>
<p>Une erreur est survenue lors de l'accès à la base de données.</p>

<a href="${ctx}/produits">Retour aux produits</a>