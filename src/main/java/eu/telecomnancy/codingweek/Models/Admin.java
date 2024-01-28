package eu.telecomnancy.codingweek.Models;

public class Admin extends User{

    /**
     * Default Constructor.
     */
    public Admin() {
        super();
    }

    /**
     * Constructor with parameters.
     * @param firstName
     * @param lastName
     * @param zipCode
     * @param postalAddress
     * @param email
     * @param password
     */
    public Admin(String firstName, String lastName, String zipCode, String postalAddress, String city,String email, String password) {
        super(firstName, lastName, zipCode, postalAddress, city, email, password);
    }
}
