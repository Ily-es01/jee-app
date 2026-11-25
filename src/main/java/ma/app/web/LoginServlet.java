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
    protected void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, res);
    }

    @Override 
    protected void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ("admin".equals(username) && "password".equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", username);
            session.setMaxInactiveInterval(30 * 60);
            res.sendRedirect(req.getContextPath()+"/dashboard");
        }
        else{
            req.setAttribute("login", username);
            req.setAttribute("erreur", "identifiant ou password incorrecte");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, res);
        }
    }
}
