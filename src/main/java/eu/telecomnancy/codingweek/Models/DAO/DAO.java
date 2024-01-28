package eu.telecomnancy.codingweek.Models.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DAO {
    protected Connection connexion;
    protected static DAO uniqueDAO;

    protected DAO() {
        try {
            // Charger le pilote JDBC
            Class.forName("org.sqlite.JDBC");

            // Établir la connexion à la base de données (ajuster le chemin selon votre configuration)
            String url = "jdbc:sqlite:database.db";
            this.connexion = DriverManager.getConnection(url);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected Connection getConnexion() {
        try {
            if (connexion.isClosed()) {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:database.db";
                this.connexion = DriverManager.getConnection(url);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return this.connexion;
    }
}
