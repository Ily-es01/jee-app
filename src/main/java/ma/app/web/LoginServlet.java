
package ma.app.web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/login.jsp")
           .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Vérification des identifiants
        if ("admin".equals(username) && "password".equals(password)) {

            // Création de la session
            HttpSession session = req.getSession();
            session.setAttribute("user", username);

            // Durée d'inactivité : 30 minutes
            session.setMaxInactiveInterval(30 * 60);

            // Récupération de la page demandée avant connexion
            String returnTo = (String) session.getAttribute("returnTo");
            session.removeAttribute("returnTo");

            // Redirection vers la page demandée ou le dashboard
            if (returnTo != null
                    && returnTo.startsWith("/")
                    && !returnTo.startsWith("//")) {

                resp.sendRedirect(req.getContextPath() + returnTo);

            } else {
                resp.sendRedirect(req.getContextPath() + "/dashboard");
            }

        } else {

            // Identifiants incorrects
            req.setAttribute("erreur", "Nom d'utilisateur ou mot de passe incorrect.");
            req.setAttribute("username", username);

            req.getRequestDispatcher("/WEB-INF/views/login.jsp")
               .forward(req, resp);
        }
    }
}
