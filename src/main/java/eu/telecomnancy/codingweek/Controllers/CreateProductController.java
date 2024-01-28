package eu.telecomnancy.codingweek.Controllers;

import eu.telecomnancy.codingweek.Helper;
import eu.telecomnancy.codingweek.Main;
import eu.telecomnancy.codingweek.Models.DAO.HoraireDAO;
import eu.telecomnancy.codingweek.Models.DAO.ProductDAO;
import eu.telecomnancy.codingweek.Models.Horaire;
import eu.telecomnancy.codingweek.Models.Product;
import eu.telecomnancy.codingweek.Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDateTime;

public class CreateProductController {
    @FXML
    private TextField title;

    @FXML
    private TextArea description;

    @FXML
    private DatePicker dateStart, dateEnd;

    @FXML
    private TextField floransCost;

    private FileChooser fileChooser;

    private File selectedFile;

    private Main main;

    private User user;

    public CreateProductController() {
    }

    public CreateProductController(Main main, User user) {
        this.main = main;
        this.user = user;
        this.fileChooser = Helper.getImagesFileChooser();
    }

    public void findFile(){
        selectedFile = fileChooser.showOpenDialog(main.getStage());
    }

    public void save() {
        Product product = new Product();
        if(!title.getText().isEmpty())
            product.setTitle(title.getText());
        if(!description.getText().isEmpty())
            product.setDescription(description.getText());
        if(!floransCost.getText().isEmpty())
            product.setFloransCost(Integer.parseInt(floransCost.getText()));
        product.setUser(user);
        LocalDateTime start = dateStart.getValue().atStartOfDay();
        LocalDateTime end = dateEnd.getValue().atStartOfDay();
        product.addDisponibility(new Horaire(start, end));
        product.setPicture(Helper.saveImage(selectedFile));
        ProductDAO.getInstance().insertProduct(product);
        Horaire horaire = new Horaire(start, end);
        HoraireDAO.getInstance().insertHoraire(horaire, product.getIdProposal());
    }
}
