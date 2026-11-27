
package ma.app.web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ma.app.dao.VenteDao;
import ma.app.dao.StockInsuffisantException;

@WebServlet("/ventes")
public class VenteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        try {
            String produitIdS = req.getParameter("produitId");
            String quantiteS = req.getParameter("quantite");

            long produitId = Long.parseLong(produitIdS);
            int quantite = Integer.parseInt(quantiteS);

            if (produitId <= 0 || quantite <= 0) {
                throw new NumberFormatException();
            }

            VenteDao dao = (VenteDao) getServletContext()
                    .getAttribute("venteDao");

            dao.vendre(produitId, quantite);

            req.getSession().setAttribute("flash",
                    "Vente effectuée avec succès !");

        } catch (NumberFormatException e) {

            req.getSession().setAttribute("flash",
                    "Données invalides.");

        } catch (StockInsuffisantException e) {

            req.getSession().setAttribute("flash",
                    "Stock insuffisant pour effectuer cette vente.");
        }

        res.sendRedirect(req.getContextPath() + "/produits");
    }
}
