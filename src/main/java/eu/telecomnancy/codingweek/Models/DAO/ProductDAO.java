package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends DAO {
    private static ProductDAO uniqueDAO;
    private ProductDAO() {
        super();
    }

    public static ProductDAO getInstance()  {
        if (uniqueDAO == null) {
            uniqueDAO = new ProductDAO();
        }
        return uniqueDAO;
    }

    public Product getProductWithId(int idProduct) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Proposal WHERE idProposal = ?"
            );
            ps.setInt(1, idProduct);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.getInt("idProposal") != 0) {
                Product res =  new Product(
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
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertProduct(Product product) {
        try {
            // heritage gestion
            ProposalDAO.getInstance().insertProposal(product);
            int lastId = ProposalDAO.getInstance().getLastIdProposal();
            product.setIdProposal(lastId);

            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Product VALUES (?)"
            );
            ps.setInt(1, lastId);
            ps.executeUpdate();
            connexion.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(Product product) {
        try {
            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM Product WHERE idProduit = ?"
            );
            ps.setInt(1, product.getIdProposal());
            ps.executeUpdate();
            ProposalDAO.getInstance().deleteProposal(product);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> getAllProducts(){
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Proposal d JOIN Product p ON (d.idProposal=p.idProduit)"
            );
            ResultSet resultSet = ps.executeQuery();
            List<Product> res = new ArrayList<>();
            while (resultSet.next()) {
                Product p = new Product(
                        resultSet.getInt("idProposal"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("picture"),
                        BasicUserDAO.getInstance().getBasicUserWithId(resultSet.getInt("idBasicUser"))
                );
                p.setFloransCost(resultSet.getInt("florans"));
                p.setDisponibilities(DisponibilityDAO.getInstance().getAllDisponibilityWithProposalId(resultSet.getInt("idProposal")));
                res.add(p);
                //res.get(res.size() - 1).addDisponibility(DisponibilityDAO.getInstance().getDisponibilityWithId(resultSet.getInt("idDisponibility")));
            }
            connexion.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
