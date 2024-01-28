package eu.telecomnancy.codingweek.Models;

import java.util.ArrayList;
import java.util.List;

public class Product extends Proposal{

    public Product(String title, String description, String picture, User user, int coutJournalier, int id) {
        super(title, description, picture, coutJournalier, user, id);
    }

    public static List<Product> getProductsDefault(){
        BasicUser user1 = new BasicUser("Emma", "Matelas", "zipcode", "", "Nancy", "jtm@gmail.com", "1234");
        List<Product> products = new ArrayList<>();
        products.add(new Product("Produit 1","desc 1","img/notification.png",user1, 3, 1));
        products.add(new Product("Produit 2","desc 2","img/profile.png",user1, 4, 1));
        return products;
    }

    public Product(){
        super();
    }

    public Product(int idProduct, String title, String description, String picture, User user) {
        super(idProduct, title, description, picture, user);
    }

    public String toString() {
        String disponibilities = "";
        if(this.getDisponibilities() == null){
            return "Product : " + this.getTitle() + " " + this.getDescription() + " " + this.getPicture() + " " + this.getUser() + " " + this.getDailyCost();
        }
        for (Disponibility disponibility : this.getDisponibilities()) {
            disponibilities += disponibility.toString() + " ";
        }
    	return "Product : " + this.getTitle() + " " + this.getDescription() + " " + this.getPicture() + " " + this.getUser() + " " + this.getDailyCost() + " " + disponibilities;
    }

    public void setFloransCost(int parseInt) {
    }
}
