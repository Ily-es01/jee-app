
package ma.app.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Comparator;
import java.util.Locale;
import java.util.ArrayList;

public class ProduitDAO {


    public List<Produit> searchByName(String q) {

    String recherche = q.toLowerCase(Locale.ROOT);

    List<Produit> resultat = new ArrayList<>();

    for (Produit p : produits.values()) {
        if (p.getNom().toLowerCase(Locale.ROOT).contains(recherche)) {
            resultat.add(p);
        }
    }

    resultat.sort(Comparator.comparing(Produit::getId));

    return resultat;
    }

    
    private final Map<Long, Produit> produits = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public ProduitDAO() {
        save(new Produit(null, "Laptop", "Dell XPS 13",
                new BigDecimal("9500.00"), 5));

        save(new Produit(null, "Souris", "Logitech MX Master",
                new BigDecimal("650.00"), 20));
    }

    // Retourner tous les produits triés par ID
    public List<Produit> findAll() {
        return produits.values()
                .stream()
                .sorted(Comparator.comparing(Produit::getId))
                .toList();
    }

    // Rechercher un produit par ID
    public Produit findById(Long id) {
        return produits.get(id);
    }

    // Ajouter ou modifier un produit
    public void save(Produit p) {
        if (p.getId() == null) {
            p.setId(sequence.incrementAndGet());
        }

        produits.put(p.getId(), p);
    }

    // Supprimer un produit
    public void delete(Long id) {
        produits.remove(id);
    }
}
