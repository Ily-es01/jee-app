package ma.app.web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/theme")
public class ThemeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String theme = req.getParameter("value");

        // Vérifier que le thème est valide
        if (!"dark".equals(theme) && !"light".equals(theme)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Créer le cookie
        Cookie cookie = new Cookie("theme", theme);
        cookie.setMaxAge(60 * 60 * 24 * 30); // 30 jours
        cookie.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());

        resp.addCookie(cookie);

        // Retourner à la page précédente
        String referer = req.getHeader("Referer");

        if (referer != null && referer.startsWith(
                req.getScheme() + "://" + req.getServerName())) {
            resp.sendRedirect(referer);
        } else {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        }
    }
}