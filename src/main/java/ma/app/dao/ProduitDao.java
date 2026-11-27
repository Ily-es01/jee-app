package ma.app.dao;

import java.util.List;
import java.util.Optional;

import ma.app.model.Produit;

public interface ProduitDao  {
    List<Produit> trouverTous(); 
    List<Produit> rechercher(String motCle, String tri);   // motCle et tri peuvent être null 
    Optional<Produit> trouverParId(long id); 
    Produit creer(Produit produit);
    void modifier(Produit produit); 
    void supprimer(long id); 
}
