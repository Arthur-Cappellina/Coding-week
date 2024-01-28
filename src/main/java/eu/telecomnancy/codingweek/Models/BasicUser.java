package eu.telecomnancy.codingweek.Models;

import eu.telecomnancy.codingweek.Helper;
import eu.telecomnancy.codingweek.Models.DAO.LoanDAO;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BasicUser extends User {
    private ArrayList<Proposal> proposals;

    //private Planning planning;
    /**
     * Default Constructor.
     */
    public BasicUser() {
        super();
        this.proposals = new ArrayList<Proposal>();
        //this.planning = new Planning();
    }

    /**
     * Constructor with parameters.
     * @param firstName
     * @param lastName
     * @param zipCode
     * @param postalAddress
     * @param email
     * @param password
     * @param proposals
     */
    public BasicUser(String firstName, String lastName, String zipCode, String postalAddress, String city, String email, String password, ArrayList<Proposal> proposals) {
        super(firstName, lastName, zipCode, postalAddress, city, email, password);
        this.proposals = proposals;
        //this.planning = new Planning();
    }

    /**
     * Constructor with parameters.
     * @param firstName
     * @param lastName
     * @param zipCode
     * @param postalAddress
     * @param email
     * @param password
     */
    public BasicUser(String firstName, String lastName, String zipCode, String postalAddress, String city, String email, String password) {
        super(firstName, lastName, zipCode, postalAddress, city, email, password);
        this.proposals = new ArrayList<Proposal>();
        //this.planning = new Planning();
    }


    public BasicUser(String firstName, String lastName, String id) {
        super(firstName, lastName, "", "", "", "", id);
    }

    public BasicUser(int id, String firstName, String lastName) {
        super(id, firstName, lastName, "", "", "", "", "");
    }
    public BasicUser(int idBasicUser, String firstName, String lastName, String zipCode, String postalAddress, String city, String email, String password) {
        super(idBasicUser, firstName, lastName, zipCode, postalAddress, city, email, password);
        this.proposals = new ArrayList<Proposal>();
    }

    public BasicUser(int idBasicUser, String firstName, String lastName, String zipCode, String postalAddress, String city, String email, String password, ArrayList<Proposal> proposals) {
        super(idBasicUser, firstName, lastName, zipCode, postalAddress, city, email, password);
        this.proposals = proposals;
    }

    /**
     * @getter
     * @return ArrayList<Proposal>
     */
    public ArrayList<Proposal> getProposals() {
        return proposals;
    }

    /**
     * @setter
     * @param proposals
     */
    public void setProposals(ArrayList<Proposal> proposals) {
        this.proposals = proposals;
    }

    /**
     * Add new proposal to the proposals ArrayList.
     * @param proposal
     */
    public void addProposal(Proposal proposal) {
        if (!this.proposals.contains(proposal))
            this.proposals.add(proposal);
    }

    /**
     * Remove a proposal from the proposals ArrayList.
     * @param proposal
     */
    public void removeProposal(Proposal proposal) {
        this.proposals.remove(proposal);
    }

    /**
     * Borrower can ask to loan an owner's proposal.
     * Data are injected in the database.
     * Returns loans object, or null if the conditions aren't respected.
     * @param proposal
     * @param startDateLoan
     * @param endDateLoan
     * @return
     */
    public Loan askToLoan(Proposal proposal, LocalDateTime startDateLoan, LocalDateTime endDateLoan) throws ParseException {
        if (Helper.checkDates(startDateLoan, endDateLoan)) {
            if (proposal.isProposalDisponible(startDateLoan, endDateLoan)) {
                if (LoanDAO.getInstance().isLoanExistBetweenDates(proposal, startDateLoan, endDateLoan)) {
                    Loan loan =  new Loan(
                            this,
                            proposal,
                            startDateLoan,
                            endDateLoan,
                            false // not accepted by the owner by default
                    );
                    LoanDAO.getInstance().insertLoan(loan);
                    return loan;
                }
            }
        }

        return null;
    }

    /**
     * Owner can accept a loan's proposition.
     * @param loan
     * @return
     */
    public void acceptLoan(Loan loan) {
        loan.setAccepted(true); // accepted
        // mettre à jour les disponibilités de l'objet
        // to do : modify database
    }

    @Override
    public String toString() {
        return "BasicUser{" +
                "proposals=" + proposals +
                '}' + super.toString();
    }
}
