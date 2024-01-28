package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Models.Horaire;
import eu.telecomnancy.codingweek.Models.RegularWeekly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

public class HoraireDAO extends DAO {
    private static HoraireDAO uniqueDAO;
    private HoraireDAO() {
        super();
    }

    public static HoraireDAO getInstance() {
        if (uniqueDAO == null) {
            uniqueDAO = new HoraireDAO();
        }
        return (HoraireDAO) uniqueDAO;
    }

    public boolean insertHoraire(Horaire horaire, int idProposal) {
        try {
            DisponibilityDAO.getInstance().insertDisponibility(idProposal);
            int lastId = DisponibilityDAO.getInstance().getLastIdDisponibility();
            horaire.setIdDisponibility(lastId);

            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Horaire (idHoraire, dateStart, dateEnd) VALUES (?, ?, ?)"
            );
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            ps.setInt(1, lastId);
            String t = horaire.getDayStart();
            ps.setString(2, horaire.getDayStart());
            ps.setString(3, horaire.getDayEnd());
            ps.executeUpdate();
            connexion.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteHoraire(Horaire horaire) {
        try {
            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM Horaire WHERE idHoraire = ?"
            );
            ps.setInt(1, horaire.getIdDisponibility());
            ps.executeUpdate();
            connexion.close();
            DisponibilityDAO.getInstance().deleteDisponibility(horaire);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Horaire getHoraireWithId(int idHoraire) {
        try {
            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Horaire WHERE idHoraire = ?"
            );
            ps.setInt(1, idHoraire);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.getInt("idHoraire") != 0) {
                Horaire res = new Horaire(
                        resultSet.getInt("idHoraire"),
                        resultSet.getString("dateStart"),
                        resultSet.getString("dateEnd")
                );
                connexion.close();
                return res;
            }
            connexion.close();
            return null;
        } catch (SQLException | ParseException e) {
            return null;
        }
    }
}
