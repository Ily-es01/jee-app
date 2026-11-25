package ma.app.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProduitDAO {
    private final Map<Long, Produit> produits = new ConcurrentHashMap<>(); 
    private final AtomicLong sequence = new AtomicLong(0);

    public ProduitDAO() { 
        save(new Produit(null, "Laptop", "Dell XPS 13", new BigDecimal("9500.00"), 5)); 
        save(new Produit(null, "Souris", "Logitech MX Master", new BigDecimal("650.00"), 20)); 
    } 

    public List<Produit> findAll()      { 
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
    public Produit findById(Long id)    { 
        return produits.get(id);
     }
    public void save(Produit p)         { 
        if (p.getId() == null) {
            Long id = sequence.incrementAndGet();
            p.setId(id);
        }
        produits.put(p.getId(), p);
    } 
    public void delete(Long id){ 
        if (id!=null) {
            produits.remove(id);
        }  
    } 

}
