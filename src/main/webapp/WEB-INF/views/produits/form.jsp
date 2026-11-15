
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Formulaire produit</title>
</head>
<body>

    <h1>
        <c:choose>
            <c:when test="${not empty produit.id}">
                Modifier le produit
            </c:when>

            <c:otherwise>
                Nouveau produit
            </c:otherwise>
        </c:choose>
    </h1>

    <form action="${ctx}/produits" method="POST">

        <!-- ID présent uniquement en modification -->
        <c:if test="${not empty produit.id}">
            <input type="hidden"
                   name="id"
                   value="<c:out value='${produit.id}'/>">
        </c:if>

        <!-- Nom -->
        <div>
            <label for="nom">Nom :</label>

            <input type="text"
                   id="nom"
                   name="nom"
                   value="<c:out value='${produit.nom}'/>"
                   minlength="2"
                   maxlength="50"
                   required>

            <c:if test="${not empty erreurs.nom}">
                <p style="color:red">
                    <c:out value="${erreurs.nom}"/>
                </p>
            </c:if>
        </div>

        <br>

        <!-- Description -->
        <div>
            <label for="description">Description :</label>

            <textarea id="description"
                      name="description"
                      maxlength="255"><c:out value="${produit.description}"/></textarea>

            <c:if test="${not empty erreurs.description}">
                <p style="color:red">
                    <c:out value="${erreurs.description}"/>
                </p>
            </c:if>
        </div>

        <br>

        <!-- Prix -->
        <div>
            <label for="prix">Prix :</label>

            <input type="number"
                   id="prix"
                   name="prix"
                   value="<c:out value='${produit.prix}'/>"
                   min="0.01"
                   step="0.01"
                   required>

            <c:if test="${not empty erreurs.prix}">
                <p style="color:red">
                    <c:out value="${erreurs.prix}"/>
                </p>
            </c:if>
        </div>

        <br>

        <!-- Stock -->
        <div>
            <label for="stock">Stock :</label>

            <input type="number"
                   id="stock"
                   name="stock"
                   value="${produit.stock}"
                   min="0"
                   step="1">

            <c:if test="${not empty erreurs.stock}">
                <p style="color:red">
                    <c:out value="${erreurs.stock}"/>
                </p>
            </c:if>
        </div>

        <br>

        <button type="submit">
            Enregistrer
        </button>

        <a href="${ctx}/produits">
            Annuler
        </a>

    </form>

</body>
</html>
