<%@ taglib prefix="c" uri="jakarta.tags.core" %> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
</head>

    <%@ include file="header.jsp" %>
    <h1>Bienvenue ${user}</h1>
    <p>
        <a href="${pageContext.request.contextPath}/produits">Produit page</a>
    </p>
    <br>
    <p>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </p>
</body>
</html>