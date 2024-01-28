package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Helper;
import eu.telecomnancy.codingweek.Models.Messages.Message;
import eu.telecomnancy.codingweek.Models.Service;
import eu.telecomnancy.codingweek.Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO extends DAO{
    private static MessageDAO uniqueDAO;

    private MessageDAO() {
        super();
    }

    public static MessageDAO getInstance() {
        if (uniqueDAO == null) {
            uniqueDAO = new MessageDAO();
        }
        return (MessageDAO) uniqueDAO;
    }

    public List<Message> getMessages(User user, User user1) {
        try {
            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Message WHERE (idSender = ? AND idReceiver = ?) OR (idSender = ? AND idReceiver = ?) ORDER BY dateSend ASC"
            );
            ps.setInt(1, user.getIdUser());
            ps.setInt(2, user1.getIdUser());
            ps.setInt(3, user1.getIdUser());
            ps.setInt(4, user.getIdUser());
            ResultSet resultSet = ps.executeQuery();

            List<Message> res = new ArrayList<>();
            while (resultSet.next()) {
                res.add(new Message(
                        BasicUserDAO.getInstance().getBasicUserWithId(resultSet.getInt("idSender")),
                        BasicUserDAO.getInstance().getBasicUserWithId(resultSet.getInt("idReceiver")),
                        resultSet.getString("content"),
                        Helper.convertDate(resultSet.getString("dateSend")),
                        resultSet.getInt("idMessage")
                ));
            }
            connection.close();
            return res;
        } catch (SQLException e) {
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Message getMessageWithId(int idMessage) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Message WHERE idMessage = ?"
            );
            ps.setInt(1, idMessage);
            ResultSet resultSet = ps.executeQuery();



            if (resultSet.getInt("idMessage") != 0) {
                        Message res = new Message(
                            BasicUserDAO.getInstance().getBasicUserWithId(resultSet.getInt("idSender")),
                            BasicUserDAO.getInstance().getBasicUserWithId(resultSet.getInt("idReceiver")),
                            resultSet.getString("content"),
                            Helper.convertDate(resultSet.getString("date")),
                            resultSet.getInt("idMessage")
                );
                connexion.close();
                return res;
            }
            connexion.close();
            return null;
        } catch (SQLException e) {
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insertMessage(Message message) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = this.getConnexion();
            // heritage gestion
            ps = connection.prepareStatement(
                    "INSERT INTO Message (idSender, idReceiver, content, dateSend) VALUES (?, ?, ?, ?)"
            );
            ps.setInt(1, message.getSender().getIdUser());
            ps.setInt(2, message.getReceiver().getIdUser());
            ps.setString(3, message.getContent());
            ps.setString(4, Helper.convertDateTime(message.getDate()));
            ps.executeUpdate();
            // commit
            connection.close();
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMessage(Message message) {
        try {
            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM Message WHERE idMessage = ?"
            );
            ps.setInt(1, message.getId());
            ps.executeUpdate();
            connection.commit();
            ps.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Message> messagesFromUser(User user){
        try{
            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Message WHERE idSender = ? OR idReceiver = ?"
            );
            ps.setInt(1, user.getIdUser());
            ps.setInt(2, user.getIdUser());
            ResultSet resultSet = ps.executeQuery();
            List<Message> messages = new ArrayList<>();
            resultSet.next();
            messages.add(new Message(
                    BasicUserDAO.getInstance().getBasicUserWithId(resultSet.getInt("idSender")),
                    BasicUserDAO.getInstance().getBasicUserWithId(resultSet.getInt("idReceiver")),
                    resultSet.getString("content"),
                    Helper.convertDateTimeToDate(resultSet.getString("dateSend")),
                    resultSet.getInt("idMessage")
            ));
            while(resultSet.next()){
                messages.add(new Message(
                        BasicUserDAO.getInstance().getBasicUserWithId(resultSet.getInt("idSender")),
                        BasicUserDAO.getInstance().getBasicUserWithId(resultSet.getInt("idReceiver")),
                        resultSet.getString("content"),
                        Helper.convertDateTimeToDate(resultSet.getString("dateSend")),
                        resultSet.getInt("idMessage")
                ));
            }
            resultSet.close();
            connection.close();
            ps.close();
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}