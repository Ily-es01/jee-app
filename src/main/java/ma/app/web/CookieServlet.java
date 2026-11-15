
package ma.app.web;

import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cookies")
public class CookieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String action = req.getParameter("action");

        if ("dark".equals(action) || "light".equals(action)) {
            Cookie cookie = new Cookie("theme", action);
            cookie.setMaxAge(30 * 24 * 60 * 60);
            cookie.setPath(req.getContextPath().isEmpty()
                    ? "/" : req.getContextPath());

            resp.addCookie(cookie);
        }

        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
