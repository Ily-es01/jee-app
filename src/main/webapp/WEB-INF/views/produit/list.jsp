<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Produits</title>
</head>

<%@ include file="../header.jsp" %>

<h1>Catalogue de produits</h1>

<c:if test="${not empty sessionScope.flash}">
    <p style="color: red;">${sessionScope.flash}</p>
</c:if>

<c:remove var="flash" scope="session"/>

<a href="${ctx}/produits?action=new">Ajouter un produit</a>

<!-- Formulaire de recherche et de tri -->
<form action="${ctx}/produits" method="get">

    <input
        type="text"
        name="motCle"
        placeholder="Rechercher un produit..."
        value="${param.motCle}"
    >

    <label for="tri">Trier par :</label>

    <select name="tri" id="tri">
        <option value="nom">Nom (A-Z)</option>
        <option value="prix">Prix croissant</option>
        <option value="prix_desc">Prix décroissant</option>
        <option value="stock">Stock</option>
    </select>

    <button type="submit">Rechercher</button>

</form>

<c:if test="${empty produits}">
    <h3>Aucun produit</h3>
</c:if>

<table>
    <tr>
        <th>Nom</th>
        <th>Description</th>
        <th>Prix</th>
        <th>Stock</th>
        <th>Actions</th>
    </tr>

    <c:forEach var="p" items="${produits}">
        <tr>
            <td>${p.nom}</td>

            <td>${p.description}</td>

            <td>
                <fmt:formatNumber value="${p.prix}" maxFractionDigits="2"/>
            </td>

            <td>
                <c:choose>
                    <c:when test="${p.stock == 0}">
                        <p style="color: red;">${p.stock}</p>
                    </c:when>
                    <c:otherwise>
                        <p>${p.stock}</p>
                    </c:otherwise>
                </c:choose>
            </td>

            <td>
                <a href="${ctx}/produits?action=edit&id=${p.id}">Edit</a>
                <br>

                <form action="${ctx}/produits?action=delete" method="post">
                    <input type="hidden" name="id" value="${p.id}">

                    <input
                        type="submit"
                        value="Supprimer"
                        onclick="return confirm('Voulez-vous supprimer ce produit ?')"
                    >
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<a href="${ctx}/dashboard">Retour au tableau de bord</a>

</body>
</html>
