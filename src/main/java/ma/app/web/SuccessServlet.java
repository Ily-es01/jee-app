package ma.app.web;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/succes")
public class SuccessServlet extends HttpServlet {
    @Override 
    protected void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException{
        HttpSession session = req.getSession();
        String utilisateur = (String) session.getAttribute("utilisateur");

        if(utilisateur == null){
            res.sendRedirect("/app/inscription"); 
            return;
        }

        res.setContentType("text/html");
        res.getWriter().println("<h1>Bienvenue "+utilisateur+" !</h1>");
    }
}
