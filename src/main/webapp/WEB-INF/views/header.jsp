<style>
    body.dark {
        background-color: #1e1e1e;
        color: white;
    }

    body.dark a {
        color: #80bfff;
    }

    body.dark input,
    body.dark textarea {
        background-color: #333;
        color: white;
        border: 1px solid #777;
    }

    body.dark table {
        border-color: white;
    }

    body.dark th,
    body.dark td {
        border-color: white;
    }
</style>

<body class="${cookie.theme.value eq 'dark' ? 'dark' : ''}">

<header>
     <a href="${pageContext.request.contextPath}/cookies?action=dark">
        Mode sombre
    </a>

    |

    <a href="${pageContext.request.contextPath}/cookies?action=light">
        Mode clair
    </a>
</header>

<hr>
