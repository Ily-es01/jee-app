package ma.app.web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServelet extends HttpServlet {
    @Override 
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        HttpSession session = req.getSession();
        String user =(String) session.getAttribute("user");


        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/dashbord.jsp").forward(req, res);
    }
}
