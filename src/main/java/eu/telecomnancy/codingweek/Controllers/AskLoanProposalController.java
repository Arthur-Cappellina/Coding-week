package eu.telecomnancy.codingweek.Controllers;

import eu.telecomnancy.codingweek.Helper;
import eu.telecomnancy.codingweek.Main;
import eu.telecomnancy.codingweek.Models.BasicUser;
import eu.telecomnancy.codingweek.Models.DAO.DisponibilityDAO;
import eu.telecomnancy.codingweek.Models.Disponibility;
import eu.telecomnancy.codingweek.Models.Loan;
import eu.telecomnancy.codingweek.Models.Proposal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AskLoanProposalController {
    private Main main;

    private Proposal proposal;

    @FXML
    private Label dailyCostProposal;

    @FXML
    private Text descriptionProposal;

    @FXML
    private Text disponibilityProposal;

    @FXML
    private Label ownerProposal;

    @FXML
    private ImageView photoProposal;

    @FXML
    private Label titreProposal;

    @FXML
    private DatePicker start_date_loan;

    @FXML
    private DatePicker end_date_loan;

    @FXML
    private Label message_erreur;

    public AskLoanProposalController(Main main, Proposal proposal) {
        this.main = main;
        this.proposal = proposal;
    }

    @FXML
    void initialize() {
        titreProposal.setText(proposal.getTitle());
        descriptionProposal.setText(proposal.getDescription());
        dailyCostProposal.setText(String.valueOf(proposal.getDailyCost()) + " florans/jour");
        ownerProposal.setText(proposal.getUser().getFirstName() + " " + proposal.getUser().getLastName());
        photoProposal.setImage(Helper.getImage(proposal.getPicture()));
        StringBuilder s = new StringBuilder();
        ArrayList<Disponibility> t = DisponibilityDAO.getInstance().getAllDisponibilityWithProposalId(proposal.getId());
        for (Disponibility dispo : DisponibilityDAO.getInstance().getAllDisponibilityWithProposalId(proposal.getIdProposal())){
            s.append(dispo.getDisponibility());
        }
        disponibilityProposal.setText(String.valueOf(s));
    }

    @FXML
    void askToLoan(ActionEvent event) throws ParseException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Loan loan = ((BasicUser)(this.main.getUser())).askToLoan(proposal, Helper.convertDate(start_date_loan.getValue().format(formatter)), Helper.convertDate(end_date_loan.getValue().format(formatter)));
        if (loan != null) {
            int cost = loan.getCost(proposal.getDailyCost());
            if(cost <= ((BasicUser)(this.main.getUser())).getFlorans()){
                message_erreur.setText("Votre demande a bien été envoyée.");
                main.showConfirmAskLoan(proposal);
            }
            else {
                message_erreur.setText("Vous n'avez pas assez de florans pour emprunter cet objet.");
            }
        }
        else {
            message_erreur.setText("Erreur, les dates ne correspondent pas.");
        }
    }


}
