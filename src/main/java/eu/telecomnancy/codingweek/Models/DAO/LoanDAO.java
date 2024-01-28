package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Helper;
import eu.telecomnancy.codingweek.Models.BasicUser;
import eu.telecomnancy.codingweek.Models.Loan;
import eu.telecomnancy.codingweek.Models.Proposal;

import java.sql.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LoanDAO extends DAO {
    private static LoanDAO uniqueDAO;
    private LoanDAO() {}


    public static LoanDAO getInstance() {
        if (uniqueDAO == null) {
            uniqueDAO = new LoanDAO();
        }
        return uniqueDAO;
    }

    public boolean insertLoan(Loan loan) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Loan (idBorrower, idProposal, startDateLoan, endDateLoan, isAccepted) VALUES (?, ?, ?, ?, ?)"
            );
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            ps.setInt(1, loan.getBorrower().getIdUser());
            ps.setInt(2, loan.getProposal().getIdProposal());
            ps.setString(3, loan.getStartDateLoan().format(formatter));
            ps.setString(4, loan.getEndDateLoan().format(formatter));
            ps.setInt(5, 0);
            ps.executeUpdate();
            int lastId = getLastIdLoan();
            loan.setIdLoan(lastId);
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteLoan(Loan loan) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM Loan WHERE idLoan = ?"
            );
            ps.setInt(1, loan.getIdLoan());
            ps.executeUpdate();
            connexion.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public Loan getLoanWithId(int idLoan) {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Loan WHERE idLoan = ?"
            );
            ps.setInt(1, idLoan);
            ResultSet resultSet = ps.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            if (resultSet.getString("idLoan") != null) {
                Loan res =  new Loan(
                        resultSet.getInt("idLoan"),
                        BasicUserDAO.getInstance().getBasicUserWithId(resultSet.getInt("idBorrower")),
                        ProposalDAO.getInstance().getProposalWithId(resultSet.getInt("idProposal")),
                        LocalDateTime.parse(resultSet.getString("startDateLoan"), formatter),
                        LocalDateTime.parse(resultSet.getString("endDateLoan"), formatter),
                        resultSet.getBoolean("isAccepted")
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

    public int getLastIdLoan() {
        try {
            Connection connection = this.getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT MAX(idLoan) as ml FROM Loan"
            );
            ResultSet resultSet = ps.executeQuery();
            int res = resultSet.getInt("ml");
            connexion.close();
            return res;
        } catch (SQLException e) {
            return -1;
        }
    }

    public boolean setLoanAccepted(Loan loan) {
        try {
            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE Loan SET isAccepted = 1 WHERE idLoan = ?"
            );
            ps.setInt(1, loan.getIdLoan());
            ps.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLoanExistBetweenDates(Proposal proposal, LocalDateTime startDate, LocalDateTime endDate) {
        // NOT TESTED !
        try {
            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Loan WHERE idProposal = ? AND ((? BETWEEN startDateLoan AND endDateLoan) OR (? BETWEEN startDateLoan AND endDateLoan))"
            );
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String startDateFormatted = startDate.format(formatter);
            String endDateFormatted = endDate.format(formatter);
            ps.setInt(1, proposal.getIdProposal());
            ps.setString(2, startDateFormatted);
            ps.setString(3, endDateFormatted);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.getInt("idProposal") != 0) {
                connection.close();
                return false;
            }
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Loan> getLoanFromBorrower(BasicUser borrower) {
        try {
            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Loan WHERE idBorrower = ? AND endDateLoan > ? AND isAccepted = 1"
            );
            ps.setInt(1, borrower.getIdUser());
            ps.setString(2, Helper.convertDate(LocalDateTime.now()));
            ArrayList<Loan> list = new ArrayList<Loan>();
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Loan loan = new Loan(
                        resultSet.getInt("idLoan"),
                        borrower,
                        ProposalDAO.getInstance().getProposalWithId(resultSet.getInt("idProposal")),
                        resultSet.getString("startDateLoan"),
                        resultSet.getString("endDateLoan"),
                        resultSet.getBoolean("isAccepted")
                );
                list.add(loan);

            }
            connexion.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Loan> getLoanToValidate(BasicUser owner) {
        try {
            Connection connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Loan l JOIN Proposal p ON (l.idProposal=p.idProposal) WHERE isAccepted = 0 AND idBasicUser = ?"
            );
            ps.setInt(1, owner.getIdUser());
            ArrayList<Loan> list = new ArrayList<Loan>();
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                list.add(
                        new Loan(
                                resultSet.getInt("idLoan"),
                                BasicUserDAO.getInstance().getBasicUserWithId(resultSet.getInt("idBorrower")),
                                ProposalDAO.getInstance().getProposalWithId(resultSet.getInt("idProposal")),
                                resultSet.getString("startDateLoan"),
                                resultSet.getString("endDateLoan"),
                                resultSet.getBoolean("isAccepted")
                        )
                );

            }
            connexion.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
