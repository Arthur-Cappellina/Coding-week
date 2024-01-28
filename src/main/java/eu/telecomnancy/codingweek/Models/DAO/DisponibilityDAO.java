package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Models.Disponibility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DisponibilityDAO extends DAO {
    private static DisponibilityDAO uniqueDAO;
    private DisponibilityDAO() {
        super();
    }

    public static DisponibilityDAO getInstance() {
        if (uniqueDAO == null) {
            uniqueDAO = new DisponibilityDAO();
        }
        return (DisponibilityDAO) uniqueDAO;
    }

    public boolean insertDisponibility(int idProposal) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Disponibility (idProposal) VALUES (?)"
            );

            ps.setInt(1, idProposal);
            ps.executeUpdate();
            connexion.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean deleteDisponibility(Disponibility disponibility) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM Disponibility WHERE idDisponibility = ?"
            );
            ps.setInt(1, disponibility.getIdDisponibility());
            ps.executeUpdate();
            connexion.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public int getLastIdDisponibility() {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT MAX(idDisponibility) as md FROM Disponibility"
            );
            ResultSet resultSet = ps.executeQuery();
            int res = resultSet.getInt("md");
            connexion.close();
            return res;
        } catch (SQLException e) {
            return -1;
        }
    }

    public Disponibility getDisponibilityWithId(int idProposal) {
        Disponibility test = HoraireDAO.getInstance().getHoraireWithId(idProposal);
        if (test != null) {
            return test;
        } else if ((test = RegularWeeklyDAO.getInstance().getRegularWeeklyWithId(idProposal)) != null) {
            return test;
        }
        return null;
    }

    public ArrayList<Disponibility> getAllDisponibilityWithProposalId(int id) {
        try {
            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Disponibility WHERE idProposal = ?"
            );
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            ArrayList<Disponibility> res = new ArrayList<Disponibility>();
            while (resultSet.next()) {
                res.add(DisponibilityDAO.getInstance().getDisponibilityWithId(resultSet.getInt("idDisponibility")));
            }
            connexion.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
