package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Models.BasicUser;
import eu.telecomnancy.codingweek.Models.Evaluation.Evaluation;
import eu.telecomnancy.codingweek.Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EvalDAO extends DAO {
    private static EvalDAO uniqueDAO;

    private EvalDAO() {
        super();
    }

    public static EvalDAO getInstance() {
        if (uniqueDAO == null) {
            uniqueDAO = new EvalDAO();
        }
        return uniqueDAO;
    }

    public boolean insertEvaluation(Evaluation evaluation) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Evaluation (idEvaluator, idEvaluated, note, comment) VALUES (?, ?, ?, ?)"
            );
            ps.setInt(1, evaluation.getEvaluator().getIdUser());
            ps.setInt(2, evaluation.getEvaluated().getIdUser());
            ps.setInt(3, evaluation.getNote());
            ps.setString(4, evaluation.getComment());
            ps.executeUpdate();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteEvaluation(Evaluation evaluation) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM Evaluation WHERE idEvaluator = ? AND idEvaluated = ?"
            );
            ps.setInt(1, evaluation.getEvaluator().getIdUser());
            ps.setInt(2, evaluation.getEvaluated().getIdUser());
            ps.executeUpdate();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isEvaluated(User evaluator, User evaluated) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Evaluation WHERE idEvaluator = ? AND idEvaluated = ?"
            );
            ps.setInt(1, evaluator.getIdUser());
            ps.setInt(2, evaluated.getIdUser());
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.getInt("idEvaluator") != 0) {
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

    public boolean hasBorrowed(User owner, User borrower) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Loan l JOIN Proposal p ON (l.idProposal = p.idProposal) WHERE l.idBorrower = ? AND p.idBasicUser = ?"
            );
            ps.setInt(1, borrower.getIdUser());
            ps.setInt(2, owner.getIdUser());
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.getInt("idLoan") != 0) {
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

    public List<Evaluation> getEvaluations(User user) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Evaluation WHERE idEvaluated = ?"
            );
            ps.setInt(1, user.getIdUser());
            ResultSet resultSet = ps.executeQuery();
            List<Evaluation> res = new ArrayList<>();
            while (resultSet.next()) {
                Evaluation e = new Evaluation(
                        BasicUserDAO.getInstance().getBasicUserWithId(resultSet.getInt("idEvaluator")),
                        user,
                        resultSet.getInt("note"),
                        resultSet.getString("comment")
                );
                res.add(e);
            }
            return res;
        } catch (SQLException e) {
            // do not return
            e.printStackTrace();
            return null;
        }
    }

}