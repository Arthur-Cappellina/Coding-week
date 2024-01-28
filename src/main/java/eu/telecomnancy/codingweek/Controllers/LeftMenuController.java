package eu.telecomnancy.codingweek.Controllers;

import eu.telecomnancy.codingweek.Main;
import eu.telecomnancy.codingweek.Models.BasicUser;
import eu.telecomnancy.codingweek.Models.DAO.BasicUserDAO;
import eu.telecomnancy.codingweek.Models.DAO.DAO;
import eu.telecomnancy.codingweek.Models.DAO.MessageDAO;
import eu.telecomnancy.codingweek.Models.DAO.ProposalDAO;
import eu.telecomnancy.codingweek.Models.Messages.Message;
import eu.telecomnancy.codingweek.Models.Proposal;
import eu.telecomnancy.codingweek.Models.User;
import javafx.event.ActionEvent;
import javafx.util.converter.DateTimeStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LeftMenuController {

    private Main main;

    private User user;

    public LeftMenuController() {
    }

    public LeftMenuController(Main main, User user) {
        this.main = main;
        this.user = user;
    }

    public void goHome() throws IOException {
        main.showHome();
    }

    public void goMessages() throws IOException {
        List<User> conversations = new ArrayList<>();
        List<Message> messages = new ArrayList<>();
        for (Message message : MessageDAO.getInstance().messagesFromUser(user)) {
            if (message.getSender().getIdUser() == user.getIdUser() || message.getReceiver().getIdUser() == user.getIdUser()) {
                messages.add(message);
            }
        }
        for(BasicUser basicUser : BasicUserDAO.getInstance().getAllUsers()) {
            if(basicUser.getIdUser() != user.getIdUser()) {
                conversations.add(basicUser);
            }
        }
        main.showMessages(conversations, messages);
    }

    public void submitProduct() throws IOException {
    	main.showSubmitProduct();
    }

    public void submitService() throws IOException {
    	main.showSubmitService();
    }

    public void searchPage() throws IOException {
        List<Proposal> proposals = ProposalDAO.getInstance().getAllProposals();
    	main.showSearchPage(1, null, proposals, null);;
    }

    public void goPlanning(ActionEvent actionEvent) throws IOException {
        main.showPlanning();
    }
}
