package ma.app.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import ma.app.model.Produit;

public class ProduitDaoMemoire implements ProduitDao {

    private final Map<Long, Produit> produits = new ConcurrentHashMap<>(); 
    private final AtomicLong sequence = new AtomicLong(0);

    public ProduitDaoMemoire() { 
        creer(new Produit(null, "Laptop", "Dell XPS 13", new BigDecimal("9500.00"), 5)); 
        creer(new Produit(null, "Souris", "Logitech MX Master", new BigDecimal("650.00"), 20)); 
    } 

    public List<Produit> trouverTous()      { 
        List<Produit> p = new ArrayList<>();
        for(Map.Entry<Long, Produit> entry : produits.entrySet()){
            p.add(entry.getValue());
        }
        Collections.sort(p,(a,b)->{
            if(a.getId() > b.getId()){return 1;}
            else if(a.getId()==b.getId()){return 0;}
            else{return -1;}
        });
        // or i can use Long.compare(a.getId(),b.getId())
        return p;

     } 
    public Optional<Produit> trouverParId(long id)    { 
        Optional<Produit> p = Optional.ofNullable(produits.get(id));
        return p;
     }
    public Produit creer(Produit p)         { 
        if (p.getId() == null) {
            Long id = sequence.incrementAndGet();
            p.setId(id);
        }
        produits.put(p.getId(), p);
        return  p;
    } 
    public void supprimer(long id){ 
            produits.remove(id);
    } 

    public void modifier(Produit produit){
        Long id = produit.getId();
        Produit p = produits.get(id);

        if (p != null) {
            produits.put(id, produit);
        }
    } 

    public List<Produit> rechercher(String motCle, String tri) {
    List<Produit> products = trouverTous();

   
    if (motCle != null && !motCle.isBlank()) {
        products.removeIf(p ->
            !p.getNom().toLowerCase().contains(motCle.toLowerCase())
        );
    }

 
    if (tri == null || tri.isBlank() || tri.equals("nom")) {
        Collections.sort(products, (a, b) ->
            a.getNom().compareToIgnoreCase(b.getNom())
        );
    }
    else if (tri.equals("prix")) {
        Collections.sort(products, (a, b) ->
            a.getPrix().compareTo(b.getPrix())
        );
    }
    else if (tri.equals("stock")) {
        Collections.sort(products, (a, b) ->
            Integer.compare(a.getStock(), b.getStock())
        );
    }
    else if (tri.equals("prix_desc")) {
        Collections.sort(products, (a, b) ->
            b.getPrix().compareTo(a.getPrix())
        );
    }

    return products;
}

}
