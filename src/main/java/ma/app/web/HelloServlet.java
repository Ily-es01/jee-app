package ma.app.web;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hello")

public class HelloServlet extends HttpServlet {
    @Override 
    protected void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException{

        String navigateur = req.getHeader("User-Agent");
        LocalDate date = LocalDate.now();
        res.setContentType("text/html;charset=UTF-8");
        res.getWriter().println("<h1>Hello Lenovo </h1>");
        res.getWriter().println("<p>"+date+"</p>");

        res.getWriter().println("<p>depuis le navigateur :"+navigateur+" </p>");
    }
}
