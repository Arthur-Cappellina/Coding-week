package eu.telecomnancy.codingweek.Controllers;

import eu.telecomnancy.codingweek.Helper;
import eu.telecomnancy.codingweek.Main;
import eu.telecomnancy.codingweek.Models.Evaluation.Evaluation;
import eu.telecomnancy.codingweek.Models.Proposal;
import eu.telecomnancy.codingweek.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

import static eu.telecomnancy.codingweek.Models.Evaluation.Evaluation.average;


@Getter
@Setter
public class AccountController {
    private Main main;

    private User user;

    @FXML
    private Label firstname;

    @FXML
    private Label lastname;

    @FXML
    private Label city;

    @FXML
    private ImageView profilPicture;

    @FXML
    private Label eval;

    @FXML
    private Button evaluateButton;

    @FXML
    private TextField note;

    public AccountController(Main main, User user) {
        this.main = main;
        this.user = user;
    }

    public void goToAccount() throws IOException {
        main.showAccount(user);
    }

    public void init() {
        firstname.setText(user.getFirstName());
        lastname.setText(user.getLastName());
        city.setText(user.getCity());
        try {
            profilPicture.setImage(Helper.getImage("img/profile.png"));
        } catch (Exception e) {
            System.out.println("Image not found");
            profilPicture.setImage(new Image(getClass().getResource("img/not-found.jpg").toExternalForm()));
        }

        evaluateButton.setDisable(!main.getUser().hasBorrowed(user));


        eval.setText("Evaluation : " + average(user) + "/5 " + "(" + user.getEvaluations().size() + " votes)");
    }

    @FXML
    public void evaluate() throws IOException {
        String noteStr = note.getText();

        try {
            Integer.parseInt(noteStr);
        } catch (NumberFormatException e) {
            return;
        }

        int noteInt = Integer.parseInt(noteStr);

        if (!Evaluation.isEvaluationValid(noteInt)) {
            return;
        }

        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluator(main.getUser());
        evaluation.setEvaluated(user);
        if (evaluation.isEvaluated()) {
            evaluation.editEvaluation(noteInt);
        } else {
            evaluation.addEvaluation(noteInt);
        }



        main.showAccount(user);
    }
}
