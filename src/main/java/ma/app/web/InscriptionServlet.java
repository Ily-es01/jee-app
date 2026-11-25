package ma.app.web;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
    @Override 
    protected void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException{
        res.setContentType("text/html;charset=UTF-8");
        res.getWriter().println("<!-- Frorm HTML -->");

    }
    @Override 
    protected void doPost(HttpServletRequest req, HttpServletResponse res )throws IOException{
        req.setCharacterEncoding("UTF-8");

        String nom = req.getParameter("nom");
        String email = req.getParameter("email");
        String ageP = req.getParameter("age");

        if (nom == null || nom.isEmpty()) {
            res.sendRedirect("?erreur=nom_vide");
            return;
        }

        if (email == null || !email.contains("@") || email.isEmpty()) {
            res.sendRedirect("?erreur=email_invalide");
            return;
        }

        int age = 0;
        try{
            age = Integer.parseInt(ageP);
            if (age < 18) {
               res.sendRedirect("?erreur=age_min"); 
               return;  
            }
        }catch(NumberFormatException e){
            res.sendRedirect("?erreur=age_invalide"); 
        }

        HttpSession session = req.getSession();
        session.setAttribute("Utilisateur", nom);

        res.sendRedirect("/app/succes");

    }
}
