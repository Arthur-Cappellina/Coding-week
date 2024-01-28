package eu.telecomnancy.codingweek.Controllers;

import eu.telecomnancy.codingweek.Helper;
import eu.telecomnancy.codingweek.Main;
import eu.telecomnancy.codingweek.Models.DAO.BasicUserDAO;
import eu.telecomnancy.codingweek.Models.DAO.DisponibilityDAO;
import eu.telecomnancy.codingweek.Models.Disponibility;
import eu.telecomnancy.codingweek.Models.Proposal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class ConsultProposal {
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
    void DemanderEmprunt(ActionEvent event) throws IOException {
        main.showAskLoan(proposal);
    }

    public ConsultProposal(Main main, Proposal proposal){
        this.main = main;
        this.proposal = proposal;
        this.titreProposal = new Label(proposal.getTitle());
        this.descriptionProposal = new Text(proposal.getDescription());
        this.dailyCostProposal = new Label(String.valueOf(proposal.getDailyCost()));
        //photoProposal = new ImageView(proposal.getPicture());
        this.ownerProposal = new Label(proposal.getUser().toString());
        this.disponibilityProposal = new Text(proposal.getDisponibilities().toString());
    }

    public void initialize(){
        titreProposal.setText(proposal.getTitle());
        descriptionProposal.setText(proposal.getDescription());
        dailyCostProposal.setText(String.valueOf(proposal.getDailyCost()));
        ownerProposal.setText(proposal.getUser().getFirstName() + " " + proposal.getUser().getLastName());
        disponibilityProposal.setText("");
        dailyCostProposal.setText(String.valueOf(proposal.getDailyCost()) + " florans/jour");
        photoProposal.setImage(Helper.getImage(proposal.getPicture()));
        StringBuilder s = new StringBuilder();
        ArrayList<Disponibility> t = DisponibilityDAO.getInstance().getAllDisponibilityWithProposalId(proposal.getId());
        for (Disponibility dispo : DisponibilityDAO.getInstance().getAllDisponibilityWithProposalId(proposal.getIdProposal())){
            s.append(dispo.getDisponibility());
        }
        disponibilityProposal.setText(String.valueOf(s));
    }

}
