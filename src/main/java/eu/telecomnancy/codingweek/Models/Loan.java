package eu.telecomnancy.codingweek.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Loan {
    private int idLoan;
    private BasicUser borrower;
    private Proposal proposal;
    private LocalDateTime startDateLoan;
    private LocalDateTime endDateLoan;
    private boolean isAccepted;

    public Loan() {

    }

    public Loan(BasicUser borrower, Proposal proposal, String startDateLoan, String endDateLoan, boolean isAccepted) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        this.idLoan = -1; // if it's just instancied in the code and not in the database
        this.borrower = borrower;
        this.proposal = proposal;
        Date start = sdf.parse(startDateLoan);
        Date end = sdf.parse(endDateLoan);
        this.startDateLoan = LocalDateTime.ofInstant(start.toInstant(), sdf.getTimeZone().toZoneId());
        this.endDateLoan = LocalDateTime.ofInstant(end.toInstant(), sdf.getTimeZone().toZoneId());
        this.isAccepted = isAccepted;
    }

    public Loan(int idLoan, BasicUser borrower, Proposal proposal, String startDateLoan, String endDateLoan, boolean isAccepted) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.idLoan = idLoan;
        this.borrower = borrower;
        this.proposal = proposal;
        Date start = sdf.parse(startDateLoan);
        Date end = sdf.parse(endDateLoan);
        this.startDateLoan = LocalDateTime.ofInstant(start.toInstant(), sdf.getTimeZone().toZoneId());
        this.endDateLoan = LocalDateTime.ofInstant(end.toInstant(), sdf.getTimeZone().toZoneId());
        this.isAccepted = isAccepted;
    }

    public Loan(BasicUser borrower, Proposal proposal, LocalDateTime startDateLoan, LocalDateTime endDateLoan, boolean isAccepted) throws ParseException {
        this.idLoan = -1; // if it's just instancied in the code and not in the database
        this.borrower = borrower;
        this.proposal = proposal;
        this.startDateLoan = startDateLoan;
        this.endDateLoan = endDateLoan;
        this.isAccepted = isAccepted;
    }

    public Loan(int idLoan, BasicUser borrower, Proposal proposal, LocalDateTime startDateLoan, LocalDateTime endDateLoan, boolean isAccepted) throws ParseException {
        this.idLoan = idLoan;
        this.borrower = borrower;
        this.proposal = proposal;
        this.startDateLoan = startDateLoan;
        this.endDateLoan = endDateLoan;
        this.isAccepted = isAccepted;
    }

    public int getIdLoan() {
        return idLoan;
    }

    public void setIdLoan(int idLoan) {
        this.idLoan = idLoan;
    }

    public BasicUser getBorrower() {
        return borrower;
    }

    public void setBorrower(BasicUser borrower) {
        this.borrower = borrower;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public LocalDateTime getStartDateLoan() {
        return startDateLoan;
    }

    public void setStartDateLoan(LocalDateTime startDateLoan) {
        this.startDateLoan = startDateLoan;
    }

    public LocalDateTime getEndDateLoan() {
        return endDateLoan;
    }

    public void setEndDateLoan(LocalDateTime endDateLoan) {
        this.endDateLoan = endDateLoan;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public int getCost(int dailyCost) {
        return dailyCost * (int) (endDateLoan.toLocalDate().toEpochDay() - startDateLoan.toLocalDate().toEpochDay());
    }
}
