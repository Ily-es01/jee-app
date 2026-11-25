<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 
<style>
        body.dark {
            background-color: #121212;
            color: #ffffff;
        }

        body.dark input,
        body.dark textarea,
        body.dark select {
            background-color: #2b2b2b;
            color: #ffffff;
            border: 1px solid #555;
        }

        body.dark a {
            color: #80bfff;
        }

        body.dark table {
            border-color: #555;
        }
</style>

<body class="${cookie.theme.value == 'dark' ? 'dark' : 'light'}">
<div>
<a href="${pageContext.request.contextPath}/theme?value=dark">Dark mode</a>
|
<a href="${pageContext.request.contextPath}/theme?value=light">Light mode</a>
</div>