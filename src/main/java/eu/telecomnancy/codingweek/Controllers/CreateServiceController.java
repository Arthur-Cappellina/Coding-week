package eu.telecomnancy.codingweek.Controllers;

import eu.telecomnancy.codingweek.Helper;
import eu.telecomnancy.codingweek.Main;
import eu.telecomnancy.codingweek.Models.DAO.ServiceDAO;
import eu.telecomnancy.codingweek.Models.Service;
import eu.telecomnancy.codingweek.Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;


public class CreateServiceController {

    @FXML
    private TextField title;

    @FXML
    private TextArea description;

    private FileChooser fileChooser;

    private File selectedFile;

    private Main main;

    private User user;

    public CreateServiceController() {
    }

    public CreateServiceController(Main main, User user) {
        this.main = main;
        this.user = user;
        this.fileChooser = Helper.getImagesFileChooser();
    }

    public void findFile(){
        selectedFile = fileChooser.showOpenDialog(main.getStage());
    }

    public void save() {
        Service service = new Service();
        service.setTitle(title.getText());
        service.setDescription(description.getText());
        service.setUser(user);
        service.setPicture(Helper.saveImage(selectedFile));
        ServiceDAO.getInstance().insertService(service);
    }
}
