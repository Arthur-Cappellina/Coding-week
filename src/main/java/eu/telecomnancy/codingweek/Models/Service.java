package eu.telecomnancy.codingweek.Models;

import java.util.ArrayList;
import java.util.List;

public class Service extends Proposal{
    public Service(String title, String description, String picture, User user, int coutJournalier, int id) {
        super(title, description, picture, coutJournalier, user, id);
    }

    public static List<Service> getServicesDefaut(){
        BasicUser user1 = new BasicUser("Emma", "Matelas", "zipcode", "", "Nancy", "jtm@gmail.com", "1234");
        List<Service> sercices = new ArrayList<>();
        sercices.add(new Service("Service 1","desc 1","img/notification.png",user1, 5, 1));
        sercices.add(new Service("Service 2","desc 2","img/profile.png",user1, 5, 1));
        return sercices;
    }

    public Service(int idService, String title, String description, String picture, User user) {
        super(idService, title, description, picture, user);
    }

    public Service(){
        super();
    }

    public String toString() {
        String disponibilities = "";
        if(this.getDisponibilities() == null){
            return "Product : " + this.getTitle() + " " + this.getDescription() + " " + this.getPicture() + " " + this.getUser() + " " + this.getDailyCost();
        }
        for (Disponibility disponibility : this.getDisponibilities()) {
            disponibilities += disponibility.toString() + " ";
        }
    	return "Service : " + this.getTitle() + " " + this.getDescription() + " " + this.getPicture() + " " + this.getUser() + " " + this.getDailyCost() + " " + disponibilities;
    }
}
