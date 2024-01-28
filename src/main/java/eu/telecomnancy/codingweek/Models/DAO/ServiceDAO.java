package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Models.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO extends DAO {
    private static ServiceDAO uniqueDAO;
    private ServiceDAO() {
        super();
    }

    public static ServiceDAO getInstance() {
        if (uniqueDAO == null) {
            uniqueDAO = new ServiceDAO();
        }
        return uniqueDAO;
    }

    public Service getServiceWithId(int idService) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Proposal WHERE idProposal = ?"
            );
            ps.setInt(1, idService);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.getInt("idProposal") != 0) {
                Service res = new Service(
                        resultSet.getInt("idProposal"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("picture"),
                        BasicUserDAO.getInstance().getBasicUserWithId(resultSet.getInt("idBasicUser"))
                );
                res.setFloransCost(resultSet.getInt("florans"));
                connexion.close();
                return res;
            }
            connexion.close();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean insertService(Service service) {
        try {
            // heritage gestion
            ProposalDAO.getInstance().insertProposal(service);
            int lastId = ProposalDAO.getInstance().getLastIdProposal();
            service.setIdProposal(lastId);

            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Service VALUES (?)"
            );
            ps.setInt(1, lastId);
            ps.executeUpdate();
            connexion.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteService(Service service) {
        try {
            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM Service WHERE idService = ?"
            );
            ps.setInt(1, service.getIdProposal());
            ps.executeUpdate();
            ProposalDAO.getInstance().deleteProposal(service);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Service> getAllServices() {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Proposal p JOIN Service s ON (p.idProposal=s.idService)"
            );
            ResultSet resultSet = ps.executeQuery();
            List<Service> res = new ArrayList<>();
            while (resultSet.next()) {
                Service s = new Service(
                        resultSet.getInt("idProposal"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("picture"),
                        BasicUserDAO.getInstance().getBasicUserWithId(resultSet.getInt("idBasicUser"))
                );
                s.setFloransCost(resultSet.getInt("florans"));
                s.setDisponibilities(DisponibilityDAO.getInstance().getAllDisponibilityWithProposalId(resultSet.getInt("idProposal")));
                res.add(s);
            }
            connexion.close();
            return res;
        } catch (SQLException e) {
            return null;
        }
    }

}
