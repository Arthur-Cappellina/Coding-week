package eu.telecomnancy.codingweek.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TopMenuController {

    @FXML
    private Label currentFLorans;

    public void setCurrentFlorans(int florans){
        currentFLorans.setText(String.valueOf(florans));
    }
}
