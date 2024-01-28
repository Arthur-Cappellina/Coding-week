package eu.telecomnancy.codingweek.Controllers;

import eu.telecomnancy.codingweek.Main;
import eu.telecomnancy.codingweek.Models.Login.Registration;
import eu.telecomnancy.codingweek.Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistrationController {

    private Main main;

    private User user;

    @FXML
    private TextField firstName, lastName, email, postalCode, city, address;

    @FXML
    private PasswordField password, passwordConfirm;

    @FXML
    private Label erreur;

    public RegistrationController() {
    }

    public RegistrationController(Main main, User user) {
        this.main = main;
        this.user = user;
    }

    public void save(){
        Registration registration = new Registration();
        registration.setFirstName(firstName.getText());
        registration.setLastName(lastName.getText());
        registration.setEmail(email.getText());
        registration.setZipCode(postalCode.getText());
        registration.setCity(city.getText());
        registration.setPostalAddress(address.getText());
        registration.setPassword(password.getText());
        registration.setPasswordConfirmation(passwordConfirm.getText());
        erreur.setVisible(false);
        try {
            registration.signup();
            main.connect(user);
        } catch (Exception e) {
            erreur.setText(e.getMessage());
            erreur.setVisible(true);
        }
    }

    public void goToLogin() throws IOException {
        main.showLogin();
    }

}
