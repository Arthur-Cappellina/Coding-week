package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Models.RegularWeekly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegularWeeklyDAO extends DAO {
    private static RegularWeeklyDAO uniqueDAO;
    private RegularWeeklyDAO() {
        super();
    }

    public static RegularWeeklyDAO getInstance() {
        if (uniqueDAO == null) {
            uniqueDAO = new RegularWeeklyDAO();
        }
        return (RegularWeeklyDAO) uniqueDAO;
    }

    public boolean insertRegularWeekly(RegularWeekly regularWeekly, int idProposal) {
        try {
            DisponibilityDAO.getInstance().insertDisponibility(idProposal);
            int lastId = DisponibilityDAO.getInstance().getLastIdDisponibility();
            regularWeekly.setIdDisponibility(lastId);

            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO RegularWeekly (idRegularWeekly, day) VALUES (?, ?)"
            );
            ps.setInt(1, regularWeekly.getIdDisponibility());
            ps.setString(2, regularWeekly.getJour());
            ps.executeUpdate();
            connexion.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteRegularWeekly(RegularWeekly regularWeekly) {
        try {
            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM RegularWeekly WHERE idRegularWeekly = ?"
            );
            ps.setInt(1, regularWeekly.getIdDisponibility());
            ps.executeUpdate();
            connexion.close();
            DisponibilityDAO.getInstance().deleteDisponibility(regularWeekly);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public RegularWeekly getRegularWeeklyWithId(int idRegularWeekly) {
        try {
            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM RegularWeekly WHERE idRegularWeekly = ?"
            );
            ps.setInt(1, idRegularWeekly);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.getInt("idRegularWeekly") != 0) {
                RegularWeekly res =  new RegularWeekly(
                        resultSet.getInt("idRegularWeekly"),
                        resultSet.getString("day")
                );
                connexion.close();
                return res;
            }
            connexion.close();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }
}
