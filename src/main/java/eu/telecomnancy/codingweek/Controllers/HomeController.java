package eu.telecomnancy.codingweek.Controllers;

import eu.telecomnancy.codingweek.Helper;
import eu.telecomnancy.codingweek.Main;
import eu.telecomnancy.codingweek.Models.*;
import eu.telecomnancy.codingweek.Models.DAO.BasicUserDAO;
import eu.telecomnancy.codingweek.Models.DAO.LoanDAO;
import eu.telecomnancy.codingweek.Models.DAO.ProposalDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

@Getter
@Setter
public class HomeController {

    @FXML
    private Label bonjour;

    @FXML
    private TableColumn<Loan, Void> accept ;

    @FXML
    private TableColumn<Loan, String> nameDemandeur;

    @FXML
    private TableColumn<Loan, String> date;

    @FXML
    private TableColumn<Loan, Void> refuse;

    @FXML
    private TableColumn<Loan, Void> consult;

    @FXML
    private TableColumn<HomeEmprunt, String> empruntIsExpired;

    @FXML
    private TableColumn<Loan, String> endEmprunt;

    @FXML
    private TableColumn<Loan, String> proprietaireEmprunt;

    @FXML
    private TableColumn<Loan, String> startEmprunt;

    @FXML
    private TableView<Loan> tableauEmprunt;

    @FXML
    private TableView<Loan> tableauProposal;

    @FXML
    private TableColumn<Loan, String> titreEmprunt;

    @FXML
    private TableColumn<Loan, String> titreProposal;

    private User user;

    private Main main;


    public HomeController(Main main, User user) {
        this.main = main;
        this.user = user;
    }

    @FXML
    void initialize() {
        bonjour.setText("Bonjour " + user.getLastName() + " " + user.getFirstName() + " !");

        // remplissage 1ere table des emprunts
        ArrayList<Loan> loans = LoanDAO.getInstance().getLoanFromBorrower((BasicUser) user);
        ObservableList<Loan> viewDataLoan = FXCollections.observableArrayList(loans);
        tableauEmprunt.setItems(viewDataLoan);
        titreEmprunt.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getProposal().getTitle()));
        proprietaireEmprunt.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getProposal().getUser().getFirstName() + " " + c.getValue().getProposal().getUser().getLastName()));
        startEmprunt.setCellValueFactory(c -> new SimpleStringProperty(Helper.convertDate(c.getValue().getStartDateLoan())));
        endEmprunt.setCellValueFactory(c -> new SimpleStringProperty(Helper.convertDate(c.getValue().getEndDateLoan())));


        // deuxieme tableau
        ArrayList<Loan> loansToValidate = LoanDAO.getInstance().getLoanToValidate((BasicUser) user);
        ObservableList<Loan> viewLoansToValidate = FXCollections.observableArrayList(loansToValidate);
        tableauProposal.setItems(viewLoansToValidate);
        titreProposal.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getProposal().getTitle()));
        nameDemandeur.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBorrower().getFirstName()+" "+c.getValue().getBorrower().getLastName()));
        date.setCellValueFactory(c->new SimpleStringProperty("Du : "+c.getValue().getStartDateLoan()+" au "+c.getValue().getEndDateLoan()));
        accept.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("Accepter");

            {
                button.setOnAction(event -> {
                    Loan loan = getTableView().getItems().get(getIndex());
                    User borrower = loan.getBorrower();
                    Proposal proposal = loan.getProposal();
                    int cost = loan.getCost(proposal.getDailyCost());
                    borrower.setFlorans(borrower.getFlorans()-loan.getCost(proposal.getDailyCost()));
                    LoanDAO.getInstance().setLoanAccepted(loan);
                    BasicUserDAO.getInstance().updateBasicUser((BasicUser) borrower);
                    try {
                        main.refresh();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });

        refuse.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("Refuser");

            {
                button.setOnAction(event -> {
                    Loan loan = getTableView().getItems().get(getIndex());
                    LoanDAO.getInstance().deleteLoan(loan);
                    try {
                        main.showHome();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });

        consult.setCellFactory(param -> new TableCell<>() {
            private final Hyperlink link = new Hyperlink("Consulter profil");

            {
                link.setOnAction(event -> {
                    Proposal proposal = getTableView().getItems().get(getIndex()).getProposal();
                    try {
                        main.showAccount(proposal.getUser());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(link);
                }
            }
        });

    }

    public void recharge() throws IOException {
        user.setFlorans(user.getFlorans()+10);
        BasicUserDAO.getInstance().updateBasicUser((BasicUser) user);
        main.refresh();
    }


//    public void addConsultButton() {
//        Callback<TableColumn<Loan, Void>, TableCell<Loan, Void>> cellFactory = new Callback<TableColumn<Loan, Void>, TableCell<Loan, Void>>() {
//            @Override
//            public TableCell<Loan, Void> call(final TableColumn<Loan, Void> param) {
//                final TableCell<Loan, Void> cell = new TableCell<Loan, Void>() {
//
//                    private final Button btn = new Button("Consulter");
//
//                    {
//                        btn.setOnAction((ActionEvent event) -> {
//                            Loan data = getTableView().getItems().get(getIndex());
//                            //System.out.println("selectedData: " + data.getDescription());
//                            try {
//                                main.showHome();
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void updateItem(Void item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setGraphic(null);
//                        } else {
//                            setGraphic(btn);
//                        }
//                    }
//                };
//                return cell;
//            }
//        };
//
//        consultProposal.setCellFactory(cellFactory);
//
//        tableauProposal.getColumns().add(consultProposal);
//    }
//
//    public void addEditButton() {
//        Callback<TableColumn<Loan, Void>, TableCell<Loan, Void>> cellFactory = new Callback<TableColumn<Loan, Void>, TableCell<Loan, Void>>() {
//            @Override
//            public TableCell<Loan, Void> call(final TableColumn<Loan, Void> param) {
//                final TableCell<Loan, Void> cell = new TableCell<Loan, Void>() {
//
//                    private final Button btn = new Button("Editer");
//
//                    {
//                        btn.setOnAction((ActionEvent event) -> {
//                            Loan data = getTableView().getItems().get(getIndex());
//                            System.out.println("selectedData: " + data.getProposal().getDescription());
//                            try {
//                                main.showHome();
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void updateItem(Void item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setGraphic(null);
//                        } else {
//                            setGraphic(btn);
//                        }
//                    }
//                };
//                return cell;
//            }
//        };
//
//        editProposal.setCellFactory(cellFactory);
//
//        tableauProposal.getColumns().add(editProposal);
//    }
//
//    public void addDeleteButton() {
//        Callback<TableColumn<Loan, Void>, TableCell<Loan, Void>> cellFactory = new Callback<TableColumn<Loan, Void>, TableCell<Loan, Void>>() {
//            @Override
//            public TableCell<Loan, Void> call(final TableColumn<Loan, Void> param) {
//                final TableCell<Loan, Void> cell = new TableCell<Loan, Void>() {
//
//                    private final Button btn = new Button("delete");
//
//                    {
//                        btn.setOnAction((ActionEvent event) -> {
//                            Loan data = getTableView().getItems().get(getIndex());
//                            System.out.println("selectedData: " + data.getProposal().getDescription());
//                            try {
//                                main.showHome();
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void updateItem(Void item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setGraphic(null);
//                        } else {
//                            setGraphic(btn);
//                        }
//                    }
//                };
//                return cell;
//            }
//        };
//
//        deleteProposal.setCellFactory(cellFactory);
//
//        tableauProposal.getColumns().add(deleteProposal);
//    }
}




