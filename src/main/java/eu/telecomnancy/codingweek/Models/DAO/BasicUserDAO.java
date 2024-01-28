package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Models.BasicUser;
import eu.telecomnancy.codingweek.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BasicUserDAO extends DAO {
    private static BasicUserDAO uniqueDAO;

    private BasicUserDAO() {
        super();
    }

    public static BasicUserDAO getInstance() {
        if (uniqueDAO == null) {
            uniqueDAO = new BasicUserDAO();
        }
        return uniqueDAO;
    }

    public boolean insertBasicUser(BasicUser user) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO BasicUser (firstName, lastName, zipCode, postalAddress, city, email, password) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getZipCode());
            ps.setString(4, user.getPostalAddress());
            ps.setString(5, user.getCity());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getPassword());
            ps.executeUpdate();
            connexion.close();
            int lastId = this.getLastIdBasicUser();
            user.setIdUser(lastId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteBasicUser(BasicUser user) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM BasicUser WHERE idBasicUser = ?"
            );
            ps.setInt(1, user.getIdUser());
            ps.executeUpdate();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public BasicUser getBasicUserWithEmail(String email) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT idBasicUser, firstName, lastName, zipCode, postalAddress, city, email, password, florans FROM BasicUser WHERE UPPER(email) = UPPER(?)"
            );
            ps.setString(1, email);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.getString("email") != null) {
                BasicUser res = new BasicUser(
                        resultSet.getInt("idBasicUser"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("zipCode"),
                        resultSet.getString("postalAddress"),
                        resultSet.getString("city"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
                res.setFlorans(resultSet.getInt("florans"));
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

    public BasicUser getBasicUserWithId(int idBasicUser) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT idBasicUser, firstName, lastName, zipCode, postalAddress, city, email, password FROM BasicUser WHERE idBasicUser = ?"
            );
            ps.setInt(1, idBasicUser);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.getString("idBasicUser") != null) {
                BasicUser b =  new BasicUser(
                        resultSet.getInt("idBasicUser"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("zipCode"),
                        resultSet.getString("postalAddress"),
                        resultSet.getString("city"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
                connection.close();
                return b;
            }
            connection.close();
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getLastIdBasicUser() {
        try {
            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT MAX(idBasicUser) as mb FROM BasicUser"
            );
            ResultSet resultSet = ps.executeQuery();
            int res = resultSet.getInt("mb");
            connexion.close();
            return res;
        } catch (SQLException e) {
            return -1;
        }
    }

    public List<BasicUser> getAllUsers(){
        try{
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM BasicUser"
            );
            ResultSet resultSet = ps.executeQuery();
            List<BasicUser> res = new ArrayList<>();
            while(resultSet.next()){
                BasicUser b = new BasicUser(
                        resultSet.getInt("idBasicUser"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("zipCode"),
                        resultSet.getString("postalAddress"),
                        resultSet.getString("city"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
                res.add(b);
            }
            connexion.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void updateBasicUser(BasicUser user){
        try{
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE BasicUser SET firstName = ?, lastName = ?, zipCode = ?, postalAddress = ?, city = ?, email = ?, password = ?, florans = ? WHERE idBasicUser = ?"
            );
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getZipCode());
            ps.setString(4, user.getPostalAddress());
            ps.setString(5, user.getCity());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getPassword());
            ps.setInt(8, user.getFlorans());
            ps.setInt(9, user.getIdUser());
            ps.executeUpdate();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}