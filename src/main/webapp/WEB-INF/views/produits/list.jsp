
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Catalogue de produits</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
        }

        .flash {
            padding: 12px;
            margin: 15px 0;
            background: #d4edda;
            color: #155724;
            border-radius: 5px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        th {
            background: #f2f2f2;
        }

        .stock-zero {
            color: red;
            font-weight: bold;
        }

        .actions {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        form {
            margin: 0;
        }

        button, input {
            padding: 7px;
        }

        .search {
            margin: 20px 0;
        }
    </style>
</head>

<%@ include file="/WEB-INF/views/header.jsp" %>

<h1>Catalogue de produits</h1>

<!-- Message flash -->
<c:if test="${not empty flash}">
    <div class="flash">
        <c:out value="${flash}"/>
    </div>
</c:if>

<!-- Ajouter un produit -->
<p>
    <a href="${ctx}/produits?action=new">
        + Ajouter un produit
    </a>
</p>

<!-- Recherche -->
<div class="search">
    <form action="${ctx}/produits" method="GET">

        <input type="text"
               name="q"
               placeholder="Rechercher un produit..."
               value="<c:out value='${q}'/>">

        <button type="submit">Rechercher</button>

        <a href="${ctx}/produits">Réinitialiser</a>

    </form>
</div>

<!-- Liste des produits -->
<c:choose>

    <c:when test="${empty produits}">
        <p>Aucun produit.</p>
    </c:when>

    <c:otherwise>

        <table>
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Description</th>
                    <th>Prix</th>
                    <th>Stock</th>
                    <th>Actions</th>
                </tr>
            </thead>

            <tbody>

                <c:forEach var="produit" items="${produits}">

                    <tr>
                        <td>
                            <c:out value="${produit.nom}"/>
                        </td>

                        <td>
                            <c:out value="${produit.description}"/>
                        </td>

                        <td>
                            <fmt:formatNumber
                                value="${produit.prix}"
                                minFractionDigits="2"
                                maxFractionDigits="2"/>
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${produit.stock == 0}">
                                    <span class="stock-zero">0</span>
                                </c:when>

                                <c:otherwise>
                                    ${produit.stock}
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td>
                            <div class="actions">

                                <!-- Modifier -->
                                <a href="${ctx}/produits?action=edit&id=${produit.id}">
                                    Éditer
                                </a>

                                <!-- Supprimer -->
                                <form action="${ctx}/produits"
                                      method="POST"
                                      onsubmit="return confirm('Voulez-vous vraiment supprimer ce produit ?');">

                                    <input type="hidden"
                                           name="action"
                                           value="delete">

                                    <input type="hidden"
                                           name="id"
                                           value="${produit.id}">

                                    <button type="submit">
                                        Supprimer
                                    </button>

                                </form>

                            </div>
                        </td>
                    </tr>

                </c:forEach>

            </tbody>
        </table>

    </c:otherwise>

</c:choose>

<br>

<a href="${ctx}/dashboard">
    Retour au tableau de bord
</a>

</body>
</html>
