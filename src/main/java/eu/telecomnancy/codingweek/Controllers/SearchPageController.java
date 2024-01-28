package eu.telecomnancy.codingweek.Controllers;

import eu.telecomnancy.codingweek.Main;
import eu.telecomnancy.codingweek.Models.Proposal;
import eu.telecomnancy.codingweek.Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchPageController {

    @FXML
    private HBox model;

    @FXML
    private VBox parent, rightModel;

    @FXML
    private Label nbPages;

    @FXML
    private TextField searchBar;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    private Main main;

    private User user;

    private List<Proposal> proposals;

    public SearchPageController(Main main, User user) {
        this.main = main;
        this.user = user;
        proposals = new ArrayList<>();
    }

    public void addAllProposals(List<Proposal> proposals) {
    	this.proposals.addAll(proposals);
    }

    public HBox getModel(){
        return model;
    }

    public VBox getParent(){
        return parent;
    }

    public VBox getRightModel() {
        return rightModel;
    }

    public Label getNbPages() {
    	return nbPages;
    }

    public TextField getSearchBar() {
    	return searchBar;
    }

    public DatePicker getStartDate() {
    	return startDate;
    }

    public DatePicker getEndDate() {
    	return endDate;
    }

    public void left() throws IOException {
        String page = nbPages.getText();
        int pageInt = Integer.parseInt(page);
        if (pageInt > 1) {
            String searchStr = searchBar.getText();
            main.showSearchPage(pageInt - 1, searchStr, proposals, this);
        }
    }

    public void right() throws IOException {
        String page = nbPages.getText();
        int pageInt = Integer.parseInt(page);
        String searchStr = searchBar.getText();
        main.showSearchPage(pageInt + 1, searchStr, proposals, this);
    }

    public void setStartDate(DatePicker startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(DatePicker endDate) {
    	this.endDate = endDate;
    }

    public void searchButton() throws IOException {
    	String searchStr = searchBar.getText();
    	main.showSearchPage(1, searchStr, proposals, this);
    }
}
