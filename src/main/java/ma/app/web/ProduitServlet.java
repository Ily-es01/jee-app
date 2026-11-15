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
import jakarta.servlet.http.HttpSession;

import ma.app.model.Produit;
import ma.app.model.ProduitDAO;

@WebServlet("/produits")
public class ProduitServlet extends HttpServlet {

    private ProduitDAO dao;

    @Override
    public void init() {
        dao = (ProduitDAO) getServletContext()
                .getAttribute("produitDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        // GET /produits : afficher la liste
        if (action == null) {

            // Bonus : recherche
            String q = req.getParameter("q");

            if (q == null || q.trim().isEmpty()) {
                req.setAttribute("produits", dao.findAll());
            } else {
                req.setAttribute("produits", dao.searchByName(q.trim()));
            }

            req.setAttribute("q", q);

            // Récupérer le message flash
            HttpSession session = req.getSession(false);

            if (session != null) {
                String flash = (String) session.getAttribute("flash");

                if (flash != null) {
                    req.setAttribute("flash", flash);
                    session.removeAttribute("flash");
                }
            }

            req.getRequestDispatcher("/WEB-INF/views/produits/list.jsp")
                    .forward(req, resp);

        }

        // GET /produits?action=new
        else if ("new".equals(action)) {

            req.setAttribute("produit", new Produit());

            req.getRequestDispatcher("/WEB-INF/views/produits/form.jsp")
                    .forward(req, resp);

        }

        // GET /produits?action=edit&id=3
        else if ("edit".equals(action)) {

            try {
                Long id = Long.parseLong(req.getParameter("id"));

                Produit produit = dao.findById(id);

                if (produit == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                req.setAttribute("produit", produit);

                req.getRequestDispatcher("/WEB-INF/views/produits/form.jsp")
                        .forward(req, resp);

            } catch (NumberFormatException | NullPointerException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        // Suppression
        if ("delete".equals(action)) {

            try {
                Long id = Long.parseLong(req.getParameter("id"));

                Produit produit = dao.findById(id);

                if (produit == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                dao.delete(id);

                req.getSession().setAttribute(
                        "flash",
                        "Produit \"" + produit.getNom() + "\" supprimé."
                );

                resp.sendRedirect(req.getContextPath() + "/produits");
                return;

            } catch (NumberFormatException | NullPointerException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }

        // Récupération des paramètres
        String idParam = req.getParameter("id");
        String nom = req.getParameter("nom");
        String description = req.getParameter("description");
        String prixParam = req.getParameter("prix");
        String stockParam = req.getParameter("stock");

        Map<String, String> erreurs = new HashMap<>();

        // Validation du nom
        if (nom == null || nom.trim().length() < 2
                || nom.trim().length() > 50) {

            erreurs.put("nom",
                    "Le nom doit contenir entre 2 et 50 caractères.");
        }

        // Validation de la description
        if (description != null && description.length() > 255) {

            erreurs.put("description",
                    "La description ne doit pas dépasser 255 caractères.");
        }

        // Validation du prix
        BigDecimal prix = null;

        try {
            prix = new BigDecimal(prixParam);

            if (prix.compareTo(BigDecimal.ZERO) <= 0) {
                erreurs.put("prix",
                        "Le prix doit être strictement positif.");
            }

        } catch (NumberFormatException | NullPointerException e) {
            erreurs.put("prix", "Veuillez saisir un prix valide.");
        }

        // Validation du stock
        int stock = 0;

        if (stockParam != null && !stockParam.trim().isEmpty()) {

            try {
                stock = Integer.parseInt(stockParam);

                if (stock < 0) {
                    erreurs.put("stock",
                            "Le stock doit être supérieur ou égal à 0.");
                }

            } catch (NumberFormatException e) {
                erreurs.put("stock",
                        "Le stock doit être un entier valide.");
            }
        }

        // Déterminer création ou modification
        boolean modification = idParam != null
                && !idParam.trim().isEmpty();

        Produit produit = new Produit();

        produit.setNom(nom != null ? nom.trim() : "");
        produit.setDescription(description);
        produit.setPrix(prix);
        produit.setStock(stock);

        if (modification) {

            try {
                Long id = Long.parseLong(idParam);

                Produit existant = dao.findById(id);

                if (existant == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                produit.setId(id);

            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }

        // S'il existe des erreurs
        if (!erreurs.isEmpty()) {

            req.setAttribute("erreurs", erreurs);
            req.setAttribute("produit", produit);

            req.getRequestDispatcher("/WEB-INF/views/produits/form.jsp")
                    .forward(req, resp);

            return;
        }

        // Enregistrement
        dao.save(produit);

        // Message flash
        String message = modification
                ? "Produit \"" + produit.getNom() + "\" modifié."
                : "Produit \"" + produit.getNom() + "\" enregistré.";

        req.getSession().setAttribute("flash", message);

        // PRG : Post / Redirect / Get
        resp.sendRedirect(req.getContextPath() + "/produits");
    }
}
