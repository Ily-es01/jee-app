package ma.app.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ma.app.dao.ProduitDaoJdbc;
import ma.app.dao.VenteDaoJdbc;


@WebListener 
public class AppInitListener implements ServletContextListener {
    
    @Override 
    public void contextInitialized(ServletContextEvent sce) { 
            sce.getServletContext().setAttribute("produitDao", new ProduitDaoJdbc());
            sce.getServletContext().setAttribute("venteDao", new VenteDaoJdbc());
    } 
}
