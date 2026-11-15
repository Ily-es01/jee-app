
package ma.app.model;

import java.math.BigDecimal;

public class Produit {

    private Long id;
    private String nom;
    private String description;
    private BigDecimal prix;
    private int stock;

    public Produit() {}

    public Produit(Long id, String nom, String description, BigDecimal prix, int stock) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public int getStock() {
        return stock;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
