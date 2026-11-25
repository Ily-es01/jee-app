 <%@ taglib prefix="c" uri="jakarta.tags.core" %>
 <!DOCTYPE html>
 <html lang="en">
 <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
 </head>
    <%@ include file="header.jsp" %>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div>
            <label for="username">username :</label>
            <input type="text" name="username" value="${login}">
        </div>
        <div>
            <label for="password">password</label>
            <input type="password" name="password">
        </div>
        <div>
            <input type="submit" value="login">
        </div>
        <c:if test="${not empty erreur}">
            <p style="color: red;">erreur ${erreur}</p>
        </c:if>
    </form>
 </body>
 </html>