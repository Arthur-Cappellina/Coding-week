package eu.telecomnancy.codingweek;

import eu.telecomnancy.codingweek.Controllers.*;
import eu.telecomnancy.codingweek.Models.*;
import eu.telecomnancy.codingweek.Models.Search.DateFilter;
import eu.telecomnancy.codingweek.Models.Search.SearchConcrete;
import eu.telecomnancy.codingweek.Controllers.ConsultProposal;
import eu.telecomnancy.codingweek.Models.Messages.Message;
import eu.telecomnancy.codingweek.Models.Proposal;
import eu.telecomnancy.codingweek.Models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import eu.telecomnancy.codingweek.Models.Search.Search;

import java.io.IOException;
import java.util.List;

import static eu.telecomnancy.codingweek.Helper.cloneVbox;

public class Main extends Application {

    private Stage stage;

    private User user;

    private BorderPane pane;

    @Override
    public void start(Stage stage) throws IOException {
        user = new BasicUser();
        pane = new BorderPane();
        Scene scene = new Scene(pane, 1150, 750);
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image(getClass().getResource("img/logo2.png").toExternalForm()));
        stage.setTitle("Waf-Waf services");
        showLogin();
    }

    public void refresh() throws IOException {
        pane.getChildren().clear();
        addMenus();
        showHome();
    }

    public void addMenus() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("top_menu.fxml"));
        pane.setTop(loader.load());
        ((TopMenuController) loader.getController()).setCurrentFlorans(user.getFlorans());
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("left_menu.fxml"));
        loader.setControllerFactory(c -> new LeftMenuController(this, user));
        pane.setLeft(loader.load());
    }


    public void showHome() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home_page.fxml"));
        loader.setControllerFactory(c -> new HomeController(this, user));
        pane.setCenter(loader.load());
    }


    public void showSubmitProduct() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        CreateProductController createProduct = new CreateProductController(this, user);
        loader.setLocation(getClass().getResource("create_product.fxml"));
        loader.setControllerFactory(c -> createProduct);
        pane.setCenter(loader.load());
    }

    public void showSubmitService() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("create_service.fxml"));
        loader.setControllerFactory(c -> new CreateServiceController(this, user));
        pane.setCenter(loader.load());
    }

    public void showSearchPage(int page, String searchStr, List<Proposal> allProposals, SearchPageController lastController) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("search_page.fxml"));
        SearchPageController searchPage = new SearchPageController(this, user);

        loader.setControllerFactory(c -> searchPage);
        pane.setCenter(loader.load());
        if(lastController != null){
            searchPage.setStartDate(lastController.getStartDate());
            searchPage.setEndDate(lastController.getEndDate());
        }
        searchPage.addAllProposals(allProposals);
        HBox model = searchPage.getModel();
        VBox rightModel = Helper.cloneVbox((VBox) model.getChildren().get(1));
        VBox parent = searchPage.getParent();


        // Search and filters
        Search searchFilter;
        if (searchStr != null) {
            searchFilter = new SearchConcrete(searchStr);
        } else {
            searchFilter = new SearchConcrete();
        }
        if (searchPage.getStartDate().getValue() != null && searchPage.getEndDate().getValue() != null) {
            searchFilter = new DateFilter(searchFilter, searchPage.getStartDate().getValue().atStartOfDay(), searchPage.getEndDate().getValue().atStartOfDay());

            DatePicker startDate = searchPage.getStartDate(); // To keep the date in the search bar
            startDate.setValue(searchPage.getStartDate().getValue());
            DatePicker endDate = searchPage.getEndDate();
            endDate.setValue(searchPage.getEndDate().getValue());
        }
        allProposals = searchFilter.filtering(allProposals);

        TextField searchBar = searchPage.getSearchBar();
        if (searchStr != null) {
            searchBar.setText(searchStr);
        }

        if (allProposals.isEmpty()) {
                Label label = new Label("Aucun résultat");
                label.setPadding(new Insets(10, 10, 10, 10));
                parent.getChildren().add(label);
        }

        model.setVisible(false);

        // Paging
        int buff = 4; // Proposal number per page
        int i = 0;

        int index = (page-1) * buff;
        int k;

        Label nbpage = searchPage.getNbPages();

        if (index >= allProposals.size()) {
            if (allProposals.size() % buff == 0) {
                index = allProposals.size() - buff;
            } else {
                index = allProposals.size() - (allProposals.size() % buff);
            }
            page = page - 1;
        }

        if (index + buff >= allProposals.size()) {
            k = allProposals.size() - index;
            nbpage.setText(String.valueOf(page));
        } else {
            k = buff;
            nbpage.setText(String.valueOf(page));
        }

        // Display
        for (Proposal p : allProposals.subList(index, index + k)) { // For each proposal in the page
            VBox rightTmp = Helper.cloneVbox(rightModel);
            rightModel.setVisible(false);
            HBox clonedModel = Helper.cloneHbox(model);
            clonedModel.setLayoutX(rightTmp.getLayoutX() + 10);
            clonedModel.setLayoutY(rightTmp.getLayoutY() + 10);

            VBox.setMargin(rightTmp, new Insets(10, 0, 10, 0));

            VBox right = cloneVbox(rightTmp);
            right.setPadding(new Insets(10, 10, 10, 10));

            ImageView img = (ImageView) clonedModel.getChildren().get(0);
            try {
                img.setImage(Helper.getImage(p.getPicture()));
            } catch (Exception e) {
                System.out.println("Image not found");
                img.setImage(new Image(getClass().getResource("img/not-found.jpg").toExternalForm()));
            }

            Label label = (Label) right.getChildren().get(0);
            label.setText(p.getTitle());

            Text label2 = (Text) right.getChildren().get(1);
            label2.setText(p.getDescription());

            Text label3 = (Text) right.getChildren().get(2);
            label3.setText(p.getFloransCost() + " Florans");

            Button button = (Button) right.getChildren().get(3);
            button.setOnAction(event -> {
                try {
                    showConsultProposal(p);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            ((ImageView) clonedModel.getChildren().get(0)).setFitWidth(100);
            ((ImageView) clonedModel.getChildren().get(0)).setFitHeight(100);

            clonedModel.getChildren().set(1, right);
            clonedModel.setTranslateY(10 * i);
            clonedModel.setVisible(true);
            parent.getChildren().add(clonedModel);
            i++;
        }
        model.getChildren().clear();
    }

    public void showSearchPage(int page, List<Proposal> proposals, SearchPageController searchPageController) throws IOException {
        showSearchPage(page, null, proposals, searchPageController);
    }

    public void showSearchPage(String searchStr, List<Proposal> proposals, SearchPageController searchPageController) throws IOException {
        showSearchPage(1, searchStr, proposals, searchPageController);
    }

    public void showsSearchPage(List<Proposal> proposals, SearchPageController searchPageController) throws IOException {
        showSearchPage(1, proposals, searchPageController);
    }

    public void showLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        LoginController loginController = new LoginController(this, user);
        loader.setControllerFactory(c -> loginController);
        pane.setCenter(loader.load());
        loginController.init();
    }

    public void connect(User user) throws IOException {
        this.user = user;
        addMenus();
        showHome();
    }


    public void showRegistration() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("register.fxml"));
        RegistrationController registrationController = new RegistrationController(this, user);
        loader.setControllerFactory(c -> registrationController);
        pane.setCenter(loader.load());
    }

    public Stage getStage() {
        return stage;
    }

    public void showConsultProposal(Proposal p) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("consult_proposal.fxml"));
        ConsultProposal consultProposal = new ConsultProposal(this, p);
        loader.setControllerFactory(c -> consultProposal);
        pane.setCenter(loader.load());
        consultProposal.initialize();
    }

    public void showPlanning() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("planning_user.fxml"));
        loader.setControllerFactory(c -> new PlanningUser(this, user));
        pane.setCenter(loader.load());
    }

    public static void main(String[] args) {
        launch();
    }


    public void showMessages(List<User> conversations, List<Message> messages) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        MessageController messageController = new MessageController(this, user, conversations, messages, conversations.get(0));
        loader.setLocation(getClass().getResource("message.fxml"));
        loader.setControllerFactory(c -> messageController);
        pane.setCenter(loader.load());
        messageController.updateAll();
    }

    public void showAskLoan(Proposal proposal) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ask_loan_proposal.fxml"));
        loader.setControllerFactory(c -> new AskLoanProposalController(this, proposal));
        pane.setCenter(loader.load());
    }

    public void showConfirmAskLoan(Proposal proposal) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("confirm_ask_loan.fxml"));
        loader.setControllerFactory(c -> new AskLoanProposalController(this, proposal));
        pane.setCenter(loader.load());
    }

    public void showAccount(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("consult_account.fxml"));
        AccountController accountController = new AccountController(this, user);
        loader.setControllerFactory(c -> accountController);
        pane.setCenter(loader.load());

        accountController.init();
    }

    public User getUser() {
        return this.user;
    }

}