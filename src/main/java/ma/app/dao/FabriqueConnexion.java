package ma.app.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class FabriqueConnexion {
    private static final Properties CONFIG = new Properties();
    static {
        try(
            InputStream in = FabriqueConnexion.class.getResourceAsStream("/db.properties"))
        {
            CONFIG.load(in);
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(IOException | ClassNotFoundException e){
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(
            CONFIG.getProperty("db.url"),
            CONFIG.getProperty("db.utilisateur"),
            CONFIG.getProperty("db.motDePasse")
        );
    }
}
