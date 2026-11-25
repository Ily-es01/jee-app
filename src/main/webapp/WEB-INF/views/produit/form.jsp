<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/> 
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Produits</title>
</head>
<%@ include file="../header.jsp" %>
    
    <form action="${ctx}/produits" method="post">
        <div>
            <label for="">Nom : </label>
            <input type="text" value="${produit.nom}" name="nom">
            <c:if test="${not empty erreur.nom}">
                <p>${erreur.nom}</p>
            </c:if>
        </div>
        <div>
            <label for="">Description : </label>
            <textarea name="description">${produit.description}</textarea>
            <c:if test="${not empty erreur.description}">
                <p>${erreur.description}</p>
            </c:if>
        </div>
        <div>
            <label for="">Prix : </label>
            <input type="number" value="${produit.prix}" name="prix">
            <c:if test="${not empty erreur.prix}">
                <p>${erreur.prix}</p>
            </c:if>
        </div>
        <div>
            <label for="">Stock : </label>
            <input type="number" value="${produit.stock}" name="stock">
            <c:if test="${not empty erreur.stock}">
                <p>${erreur.stock}</p>
            </c:if>
        </div>
        <input type="hidden" value="${produit.id}" name="id">
        <div>
            <c:choose>
                <c:when test="${not empty produit.id}">
                    <input type="submit" value="Modifier">
                </c:when>
                <c:otherwise>
                    <input type="submit" value="Creer">
                </c:otherwise>
            </c:choose>
        </div>
    </form>

</body>
</html>