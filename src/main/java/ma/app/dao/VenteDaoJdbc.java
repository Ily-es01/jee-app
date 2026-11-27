package ma.app.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VenteDaoJdbc implements VenteDao {

    @Override
    public void vendre(long produitId, int quantite) {

        String sql1 = "SELECT prix FROM produit WHERE id = ?";

        String sql2 = "UPDATE produit SET stock = stock - ? "
                    + "WHERE id = ? AND stock >= ?";

        String sql3 = "INSERT INTO vente (produit_id, quantite, prix_unitaire) "
                    + "VALUES (?, ?, ?)";

        BigDecimal prix;

        try (Connection conn = FabriqueConnexion.getConnection()) {

            conn.setAutoCommit(false);

            try (
                PreparedStatement stmnt = conn.prepareStatement(sql1);
                PreparedStatement stmnt2 = conn.prepareStatement(sql2);
                PreparedStatement stmnt3 = conn.prepareStatement(sql3)
            ) {

                // 1. SELECT prix
                stmnt.setLong(1, produitId);

                try (ResultSet st = stmnt.executeQuery()) {

                    if (st.next()) {
                        prix = st.getBigDecimal("prix");
                    } else {
                        throw new IllegalArgumentException("Produit introuvable");
                    }
                }

                // 2. UPDATE stock
                stmnt2.setInt(1, quantite);
                stmnt2.setLong(2, produitId);
                stmnt2.setInt(3, quantite);

                int row = stmnt2.executeUpdate();

                if (row == 0) {
                    throw new StockInsuffisantException();
                }

                // 3. INSERT vente
                stmnt3.setLong(1, produitId);
                stmnt3.setInt(2, quantite);
                stmnt3.setBigDecimal(3, prix);

                stmnt3.executeUpdate();

                // 4. COMMIT
                conn.commit();

            } catch (Exception e) {

                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new DonneesException(e);
        }
    }
}