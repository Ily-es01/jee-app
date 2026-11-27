package ma.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import ma.app.model.Produit;

public class ProduitDaoJdbc implements ProduitDao{
    
    private Produit versProduit(ResultSet rs) throws SQLException {
        Produit p = new Produit();

        p.setId(rs.getLong("id"));
        p.setNom(rs.getString("nom"));
        p.setDescription(rs.getString("description"));
        p.setPrix(rs.getBigDecimal("prix"));
        p.setStock(rs.getInt("stock"));

        return p;
    }
    
    @Override 
    public List<Produit> trouverTous(){
        List<Produit> prod = new ArrayList<>();
        String sql = "SELECT id, nom, description,prix, stock FROM produit";
        try(
            Connection conn = FabriqueConnexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet st = ps.executeQuery();
        ){
            while(st.next()){
                Produit p = versProduit(st);
                prod.add(p);
            }
            return prod;
            
        }catch(SQLException e){
            throw new DonneesException(e);
        }
    }

    @Override 
    public Optional<Produit> trouverParId(long id){
        String sql = "SELECT id, nom, description ,prix, stock FROM produit WHERE id=?";
        
        try(
            Connection conn = FabriqueConnexion.getConnection();
            PreparedStatement stmnt = conn.prepareStatement(sql);
        ){
             stmnt.setLong(1, id);
                try(
                    ResultSet st = stmnt.executeQuery();
                ){
                     if (st.next()) {
                    return Optional.of(versProduit(st));
             }
                return Optional.empty();}
        
        }catch(SQLException e){
            throw new DonneesException(e);
        }
     }
    

    @Override 
    public Produit creer(Produit produit){
        String sql = "INSERT INTO produit (nom, description ,prix, stock) VALUES (?, ?, ?, ?)";
        try(
            Connection conn = FabriqueConnexion.getConnection();
            PreparedStatement stmnt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ){
            stmnt.setString(1, produit.getNom());
            stmnt.setString(2, produit.getDescription());
            stmnt.setBigDecimal(3, produit.getPrix());
            stmnt.setInt(4, produit.getStock());
            stmnt.executeUpdate();
            try(
                ResultSet st = stmnt.getGeneratedKeys()
            ){
                if (st.next()) {
                    produit.setId(st.getLong(1));
                }
        }
            return produit;
        }catch(SQLException e){
            throw new DonneesException(e);
        }
    }

    @Override 
    public void modifier(Produit produit){
        String sql = "UPDATE produit SET nom=?, description = ?, prix= ?, stock= ? WHERE id =? ";
        try(
            Connection conn = FabriqueConnexion.getConnection();
            PreparedStatement stmnt = conn.prepareStatement(sql);
        ){
            stmnt.setString(1, produit.getNom());
            stmnt.setString(2, produit.getDescription());
            stmnt.setBigDecimal(3, produit.getPrix());
            stmnt.setInt(4, produit.getStock());
            stmnt.setLong(5, produit.getId());
            stmnt.executeUpdate();
            
        }catch(SQLException e){
            throw new DonneesException(e);
        }
    }

    @Override 
    public void supprimer(long id){
        String sql = "DELETE FROM produit WHERE id = ?";
        try(
            Connection conn = FabriqueConnexion.getConnection();
            PreparedStatement stmnt = conn.prepareStatement(sql);
        ) 
        {
            stmnt.setLong(1, id);
            stmnt.executeUpdate();
        } catch (SQLException e) {
            if(e.getErrorCode()==1451){
                throw new  ContrainteException(e);
            }
            else{
                throw new DonneesException(e);
            }
        }
    }

   @Override
    public List<Produit> rechercher(String motCle, String tri) {

        String orderBy;

        if (tri == null || tri.isBlank() || "nom".equalsIgnoreCase(tri)) {
            orderBy = "nom";
        } else if ("prix".equalsIgnoreCase(tri)) {
            orderBy = "prix";
        } else if ("prix_desc".equalsIgnoreCase(tri)) {
            orderBy = "prix DESC";
        } else if ("stock".equalsIgnoreCase(tri)) {
            orderBy = "stock";
        } else {
            throw new IllegalArgumentException("Tri invalide : " + tri);
        }

        String sql = "SELECT id, nom, description, prix, stock FROM produit "
                + "WHERE nom LIKE ? ORDER BY " + orderBy;

        List<Produit> prod = new ArrayList<>();

        try (
            Connection conn = FabriqueConnexion.getConnection();
            PreparedStatement stmnt = conn.prepareStatement(sql)
        ) {
            stmnt.setString(1, "%" + (motCle == null ? "" : motCle) + "%");

            try (ResultSet st = stmnt.executeQuery()) {
                while (st.next()) {
                    prod.add(versProduit(st));
                }
            }

            return prod;

        } catch (SQLException e) {
            throw new DonneesException(e);
        }
    }
}    

