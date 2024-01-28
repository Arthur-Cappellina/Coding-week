package eu.telecomnancy.codingweek.Controllers;

import eu.telecomnancy.codingweek.Main;
import eu.telecomnancy.codingweek.Models.Login.Login;
import eu.telecomnancy.codingweek.Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    private Main main;

    private User user;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label erreur;

    public LoginController() {
    }

    public LoginController(Main main, User user) {
        this.main = main;
        this.user = user;
    }

    public void init(){
        erreur.setVisible(false);
    }

    public void connect() {
        Login login = new Login(username.getText(), password.getText());
        erreur.setVisible(false);
        try{
            main.connect(login.login());
        } catch (Exception e){
            erreur.setVisible(true);
            erreur.setText(e.getMessage());
        }
    }

    public void goToRegistration() throws IOException {
        main.showRegistration();
    }

    public void fastLog(){
        Login login = new Login("username@gmail.com", "AAA222ccc@");
        erreur.setVisible(false);
        try{
            main.connect(login.login());
        } catch (Exception e){
            e.printStackTrace();
            erreur.setVisible(true);
            erreur.setText(e.getMessage());
        }
    }

}
