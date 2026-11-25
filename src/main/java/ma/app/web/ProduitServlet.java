
package ma.app.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ma.app.model.Produit;
import ma.app.model.ProduitDAO;

@WebServlet("/produits")
public class ProduitServlet extends HttpServlet {

    private ProduitDAO dao;

    @Override
    public void init() throws ServletException {
        dao = (ProduitDAO) getServletContext()
                .getAttribute("produitDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        String action = req.getParameter("action");

        if (action == null) {
            action = "list";
        }

        if ("list".equals(action)) {

            req.setAttribute("produits", dao.findAll());

            req.getRequestDispatcher(
                    "WEB-INF/views/produit/list.jsp"
            ).forward(req, res);

        } else if ("new".equals(action)) {

            req.getRequestDispatcher(
                    "WEB-INF/views/produit/form.jsp"
            ).forward(req, res);

        } else if ("edit".equals(action)) {

            try {
                Long id = Long.parseLong(req.getParameter("id"));

                Produit prod = dao.findById(id);

                if (prod != null) {
                    req.setAttribute("produit", prod);

                    req.getRequestDispatcher(
                            "WEB-INF/views/produit/form.jsp"
                    ).forward(req, res);

                } else {
                    res.sendError(HttpServletResponse.SC_NOT_FOUND);
                }

            } catch (NumberFormatException e) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");

        Map<String, String> erreurs = new HashMap<>();

        String action = req.getParameter("action");

        //DELETE
        if ("delete".equals(action)) {

            try {
                Long id = Long.parseLong(req.getParameter("id"));

                Produit produit = dao.findById(id);

                if (produit == null) {
                    res.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                dao.delete(id);

                req.getSession().setAttribute(
                        "flash", "Produit supprimé avec succès."
                );

                res.sendRedirect(req.getContextPath() + "/produits");
                return;

            } catch (NumberFormatException e) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }

     
        else if (action == null || action.isEmpty()) {

            String idP = req.getParameter("id");
            String nom = req.getParameter("nom");
            String description = req.getParameter("description");
            String prixR = req.getParameter("prix");
            String stockR = req.getParameter("stock");

   
            nom = (nom == null) ? "" : nom.trim();

            description = (description == null)
                    ? "" : description.trim();

            prixR = (prixR == null) ? "" : prixR.trim();

            stockR = (stockR == null) ? "" : stockR.trim();

     
           // VALIDATE NAME
           
            if (nom.length() < 2 || nom.length() > 50) {
                erreurs.put(
                        "nom",
                        "Le nom doit contenir entre 2 et 50 caractères."
                );
            }


            //  VALIDATE DESCRIPTION
    
            if (description.length() > 255) {
                erreurs.put(
                        "description",
                        "255 caractères maximum."
                );
            }


            //  VALIDATE PRICE

            BigDecimal prix = BigDecimal.ZERO;

            if (prixR.isEmpty()) {

                erreurs.put("prix", "Le prix est obligatoire.");

            } else {

                try {
                    prix = new BigDecimal(prixR);

                    if (prix.compareTo(BigDecimal.ZERO) <= 0) {
                        erreurs.put(
                                "prix",
                                "Le prix doit être strictement positif."
                        );
                    }

                } catch (NumberFormatException e) {
                    erreurs.put(
                            "prix",
                            "Veuillez saisir un prix valide."
                    );
                }
            }

      
            //  VALIDATE STOCK
  
            int stock = 0;

            if (!stockR.isEmpty()) {

                try {
                    stock = Integer.parseInt(stockR);

                    if (stock < 0) {
                        erreurs.put(
                                "stock",
                                "Le stock ne peut pas être négatif."
                        );
                    }

                } catch (NumberFormatException e) {
                    erreurs.put(
                            "stock",
                            "Veuillez saisir un stock entier valide."
                    );
                }
            }

            //  PARSE ID
            Long id = null;

            if (idP != null && !idP.trim().isEmpty()) {

                try {
                    id = Long.parseLong(idP);

                } catch (NumberFormatException e) {
                    res.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                // Ensure the product exists when updating
                if (dao.findById(id) == null) {
                    res.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            }

            //  CREATE PRODUIT OBJECT
            Produit produit = new Produit(
                    id, nom, description, prix, stock
            );

            req.setAttribute("prixSaisi", prixR);
            req.setAttribute("stockSaisi", stockR);

            // CHECK VALIDATION ERRORS
             if (!erreurs.isEmpty()) {

                req.setAttribute("erreurs", erreurs);
                req.setAttribute("produit", produit);

                req.getRequestDispatcher(
                        "WEB-INF/views/produit/form.jsp"
                ).forward(req, res);

                return;
            }

            //  SAVE PRODUCT
            dao.save(produit);

            String message = (id == null)
                    ? "Produit créé avec succès."
                    : "Produit modifié avec succès.";

            req.getSession().setAttribute("flash", message);

            res.sendRedirect(req.getContextPath() + "/produits");
            return;
        }

        //  UNKNOWN ACTION
        else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
