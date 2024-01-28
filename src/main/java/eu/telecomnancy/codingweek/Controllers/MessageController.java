package eu.telecomnancy.codingweek.Controllers;

import eu.telecomnancy.codingweek.Helper;
import eu.telecomnancy.codingweek.Main;
import eu.telecomnancy.codingweek.Models.DAO.BasicUserDAO;
import eu.telecomnancy.codingweek.Models.DAO.MessageDAO;
import eu.telecomnancy.codingweek.Models.Messages.Message;
import eu.telecomnancy.codingweek.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

public class MessageController {

    private Main main;

    private User user;

    @FXML
    private VBox conversationParent;

    @FXML
    private Label currentConversationPerson;

    @FXML
    private VBox messagesParent;

    private User currentConversation;
    private List<Message> messagesList;

    private List<User> usersList;

    @FXML
    private TextField messageField;

    @FXML
    private ScrollPane scrollPaneMessage;

    public MessageController(Main main, User user, List<User> conversations, List<Message> messages, User currentConversation) {
        this.main = main;
        this.user = user;
        this.usersList = conversations;
        this.messagesList = messages;
        this.currentConversation = currentConversation;
    }

    public MessageController() {
    }

    public void updateAll(){
        conversationParent.getChildren().clear();
        messagesParent.getChildren().clear();
        addAllConversations(usersList);
        addAllMessages(messagesList);
        setConversationsPerson(currentConversation.getFirstName() + " " + currentConversation.getLastName());
        scrollPaneMessage.vvalueProperty().bind(scrollPaneMessage.heightProperty());
    }

    public void setConversationsPerson(String person) {
        currentConversationPerson.setText(person);
    }

    public void addConversation(HBox conversation) {
        conversationParent.getChildren().add(conversation);
    }

    public void addMessages(VBox message) {
        messagesParent.getChildren().add(message);
    }

    public void addAllConversations(List<User> conversations) {
        for (User user : conversations) {
            addConversation(createHboxPerson(user));
        }
    }

    public void addAllMessages(List<Message> messages) {
        for (Message message : messages) {
            addMessages(createVBoxMessage(message));
        }
    }

    public HBox createHboxPerson(User user) {
        HBox hbox = new HBox();
        if(user == currentConversation){
            hbox.setStyle("-fx-background-color: #e6e6e6;");
        }
        hbox.setPrefHeight(100.0);
        hbox.setPrefWidth(200.0);
        hbox.setAlignment(javafx.geometry.Pos.CENTER);

        // Création de l'ImageView
        ImageView imageView = new ImageView();
        imageView.setFitHeight(75.0);
        imageView.setFitWidth(75.0);
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);
        imageView.setImage(new Image(main.getClass().getResource("img/profile.png").toExternalForm()));

        // Création du Label
        Hyperlink label = new Hyperlink(user.getFirstName() + " " + user.getLastName());
        label.setPrefHeight(59.0);
        label.setPrefWidth(130.0);
        label.setFont(Font.font("Poppins Regular", 18.0));
        label.setTextFill(javafx.scene.paint.Color.valueOf("#000000"));
        label.setOnAction((ActionEvent event) -> {
            currentConversation = user;
            messagesList = MessageDAO.getInstance().getMessages(user, this.user);
            updateAll();
        });

        HBox.setMargin(label, new Insets(0, 0, 0, 10.0));  // Ajout de la marge à gauche

        // Ajout des éléments à la HBox
        hbox.getChildren().addAll(imageView, label);

        // Ajout des marges à la HBox
        hbox.setPadding(new Insets(10.0));

        return hbox;
    }

    public VBox createVBoxMessage(Message m) {
        VBox vbox = new VBox();
        vbox.setMaxWidth(680.0);

        // Création du Label du message
        Label messageLabel = new Label(m.getContent());
        messageLabel.setFont(new Font(16.0));
        messageLabel.setPadding(new Insets(10.0));
        VBox.setMargin(messageLabel, new Insets(10.0, 10.0, 0, 10.0));
        if(m.getSender().getIdUser() == this.user.getIdUser()){
            vbox.setAlignment(Pos.TOP_RIGHT);
            // set color to white
            messageLabel.textFillProperty().setValue(javafx.scene.paint.Color.WHITE);
            messageLabel.setStyle("-fx-background-color: #6C75D5; -fx-background-radius: 20;");

        }
        else
            messageLabel.setStyle("-fx-background-color: #E8E7EC; -fx-background-radius: 20;");

        // Création du Label de la date
        String dateFormat = m.getDate().getDayOfMonth() + "/" + m.getDate().getMonthValue() + "/" + m.getDate().getYear() + " " + m.getDate().getHour() + ":" + m.getDate().getMinute();
        Label dateLabel = new Label(dateFormat);
        VBox.setMargin(dateLabel, new Insets(5.0, 0, 0, 20.0));

        // Ajout des éléments au VBox
        vbox.getChildren().addAll(messageLabel, dateLabel);

        return vbox;
    }

    public void send(){
        String message = messageField.getText();
        if(message != null && !message.isEmpty()){
            Message m = new Message(user, currentConversation, message, Helper.convertDateTimeNow(), 1);
            MessageDAO.getInstance().insertMessage(m);
            messagesList.add(m);
            addMessages(createVBoxMessage(m));
            messageField.clear();
            updateAll();
        }
    }

    public void onEnter(KeyEvent keyEvent) {
        if(keyEvent.getCode().toString().equals("ENTER")){
            send();
        }
    }
}
