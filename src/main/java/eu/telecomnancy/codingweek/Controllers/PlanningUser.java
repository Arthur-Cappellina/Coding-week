package eu.telecomnancy.codingweek.Controllers;

import eu.telecomnancy.codingweek.Main;
import eu.telecomnancy.codingweek.Models.Planning;
import eu.telecomnancy.codingweek.Models.RegularWeekly;
import eu.telecomnancy.codingweek.Models.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

import java.util.Objects;

public class PlanningUser {

    @FXML
    private Label dimanche;

    @FXML
    private Label jeudi;

    @FXML
    private Label lundi;

    @FXML
    private Label mardi;

    @FXML
    private Label mercredi;

    @FXML
    private Label samedi;

    @FXML
    private Label vendredi;

    private Main main;

    private User user;

    public PlanningUser(Main main, User user) {
        this.main = main;
        this.user = user;
        this.lundi = new Label();
        this.mardi = new Label();
        this.mercredi = new Label();
        this.jeudi = new Label();
        this.vendredi = new Label();
        this.samedi = new Label();
        this.dimanche = new Label();
        this.initialize();
        MenuItem addLundi = new MenuItem("Lundi");
    }
    public void initialize(){
        this.lundi.setText("Indisponible");
        this.mardi.setText("Indisponible");
        this.mercredi.setText("Indisponible");
        this.jeudi.setText("Indisponible");
        this.vendredi.setText("Indisponible");
        this.samedi.setText("Indisponible");
        this.dimanche.setText("Indisponible");
        for (RegularWeekly rg : user.getPlanning().getDays()){
            switch (rg.getJour()) {
                case "MONDAY":
                    this.lundi.setText("Disponible");
                    break;
                case "TUESDAY":
                    this.mardi.setText("Disponible");
                    break;
                case "WEDNESDAY":
                    this.mercredi.setText("Disponible");
                    break;
                case "THURSDAY":
                    this.jeudi.setText("Disponible");
                    break;
                case "FRIDAY":
                    this.vendredi.setText("Disponible");
                    break;
                case "SATURDAY":
                    this.samedi.setText("Disponible");
                    break;
                case "SUNDAY":
                    this.dimanche.setText("Disponible");
                    break;
            }

        }
    }
    public void addLundi(ActionEvent actionEvent) {
        this.lundi.setText("Disponible");
    }

    public void addMardi(ActionEvent actionEvent) {
        this.mardi.setText("Disponible");
    }

    public void addMercredi(ActionEvent actionEvent) {
        this.mercredi.setText("Disponible");
    }

    public void addJeudi(ActionEvent actionEvent) {
        this.jeudi.setText("Disponible");
    }

    public void addVendredi(ActionEvent actionEvent) {
        this.vendredi.setText("Disponible");
    }

    public void addSamedi(ActionEvent actionEvent) {
        this.samedi.setText("Disponible");
    }
    public void addDimanche(ActionEvent actionEvent) {
        this.dimanche.setText("Disponible");
    }

    public void removeLundi(ActionEvent actionEvent) {
        this.lundi.setText("Indisponible");
    }

    public void removeMardi(ActionEvent actionEvent) {
        this.mardi.setText("Indisponible");
    }

    public void removeMercredi(ActionEvent actionEvent) {
        this.mercredi.setText("Indisponible");
    }

    public void removeJeudi(ActionEvent actionEvent) {
        this.jeudi.setText("Indisponible");
    }

    public void removeVendredi(ActionEvent actionEvent) {
        this.vendredi.setText("Indisponible");
    }

    public void removeSamedi(ActionEvent actionEvent) {
        this.samedi.setText("Indisponible");
    }
    public void removeDimanche(ActionEvent actionEvent) {
        this.dimanche.setText("Indisponible");
    }


    public void comeback(ActionEvent actionEvent) {
        this.initialize();
    }

    public void save(ActionEvent actionEvent) { // Il reste à connecter le planning à la BD
        Planning pu = new Planning();
        if (this.lundi.toString().contains("Disponible")){
            pu.addDay(new RegularWeekly("MONDAY"));
        }
        if (this.mardi.toString().contains("Disponible")){
            pu.addDay(new RegularWeekly("TUESDAY"));
        }
        if (this.mercredi.toString().contains("Disponible")){
            pu.addDay(new RegularWeekly("WEDNESDAY"));
        }
        if (this.jeudi.toString().contains("Disponible")){
            pu.addDay(new RegularWeekly("THURSDAY"));
        }
        if (this.vendredi.toString().contains("Disponible")){
            pu.addDay(new RegularWeekly("FRIDAY"));
        }
        if (this.samedi.toString().contains("Disponible")){
            pu.addDay(new RegularWeekly("SATURDAY"));
        }
        if (this.dimanche.toString().contains("Disponible")){
            pu.addDay(new RegularWeekly("SUNDAY"));
        }
        user.setPlanning(pu);
    }
}


