package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Models.Product;
import eu.telecomnancy.codingweek.Models.Proposal;
import eu.telecomnancy.codingweek.Models.Service;
import eu.telecomnancy.codingweek.Principal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProposalDAO extends DAO {
    private static ProposalDAO uniqueDAO;

    private ProposalDAO() {
        super();
    }
    public static ProposalDAO getInstance() {
        if (uniqueDAO == null) {
            uniqueDAO = new ProposalDAO();
        }
        return uniqueDAO;
    }

    public boolean insertProposal(Proposal proposal) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Proposal (idBasicUser, title, description, picture, florans) VALUES (?, ?, ?, ?, ?)"
            );
            ps.setInt(1, proposal.getUser().getIdUser());
            ps.setString(2, proposal.getTitle());
            ps.setString(3, proposal.getDescription());
            ps.setString(4, proposal.getPicture());
            ps.setInt(5, proposal.getFloransCost());
            ps.executeUpdate();
            connexion.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteProposal(Proposal proposal) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM Proposal WHERE idProposal = ?"
            );
            ps.setInt(1, proposal.getIdProposal());
            ps.executeUpdate();
            connexion.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public int getLastIdProposal() {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT MAX(idProposal) as mp FROM Proposal"
            );
            ResultSet resultSet = ps.executeQuery();
            int res = resultSet.getInt("mp");
            connexion.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean isInstanceOfProduct(int idProposal) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Product WHERE idProduit = ?"
            );
            ps.setInt(1, idProposal);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.getInt("idProduit") != 0) {
                connexion.close();
                return true;
            }
            connexion.close();
            return false;
        } catch (SQLException e) {
            // do not return
            e.printStackTrace();
            return false;
        }
    }

    public boolean isInstanceOfService(int idProposal) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Service WHERE idService = ?"
            );
            ps.setInt(1, idProposal);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.getString("idService") != null) {
                connexion.close();
                return true;
            }
            connexion.close();
            return false;
        } catch (SQLException e) {
            // do not return
            return false;
        }
    }

    public Proposal getProposalWithId(int idProposal) {
        if (isInstanceOfProduct(idProposal))
            return ProductDAO.getInstance().getProductWithId(idProposal);
        else if (isInstanceOfService(idProposal))
            return ServiceDAO.getInstance().getServiceWithId(idProposal);
        else
            return null;
    }

    public List<Proposal> getAllProposals(){
        List<Proposal> proposals = new ArrayList<>();
        List<Product> products = ProductDAO.getInstance().getAllProducts();
        List<Service> services = ServiceDAO.getInstance().getAllServices();
        proposals.addAll(products);
        proposals.addAll(services);
        return proposals;
    }

}
