package eu.telecomnancy.codingweek.Models;

import eu.telecomnancy.codingweek.Models.DAO.EvalDAO;
import eu.telecomnancy.codingweek.Models.Evaluation.Evaluation;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    private int idUser;
    private String firstName;
    private String lastName;
    private String zipCode;
    private String postalAddress;

    private String city;
    private String email;
    private String password;

    private Planning planning;

    private List<Evaluation> evaluations = new ArrayList<>();

    private int florans;

    /**
     * Default Constructor.
     */
    public User() {}

    /**
     * Constructor with parameters.
     * @param firstName
     * @param lastName
     * @param zipCode
     * @param postalAddress
     * @param email
     * @param password
     */
    public User(String firstName, String lastName, String zipCode, String postalAddress, String city, String email, String password) {
        this.idUser = -1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.postalAddress = postalAddress;
        this.city = city;
        this.email = email;
        this.password = password;
    }

    public User(int idUser, String firstName, String lastName, String zipCode, String postalAddress, String city, String email, String password) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.postalAddress = postalAddress;
        this.city = city;
        this.email = email;
        this.password = password;
        this.planning = new Planning();
    }

    /**
     * @getter
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @setter
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @getter
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @setter
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @getter
     * @return String
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @setter
     * @param zipCode
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @getter
     * @return String
     */
    public String getPostalAddress() {
        return postalAddress;
    }

    /**
     * @setter
     * @param postalAddress
     */
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    /**
     * @getter
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * @setter
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @getter
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * @setter
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @getter
     * @return String
     */
    public String getCity() {
        return city;
    }

    /**
     * @setter
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    public Planning getPlanning() {
        return planning;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public List<Evaluation> getEvaluations() {
        EvalDAO evalDAO = EvalDAO.getInstance();
        evaluations = evalDAO.getEvaluations(this);
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    @Override
    public String toString() {

            return "User{" +
                    "idUser=" + idUser +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", zipCode='" + zipCode + '\'' +
                    ", postalAddress='" + postalAddress + '\'' +
                    ", city='" + city + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", planning=" + planning +
                    '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User u = (User) obj;
            return this.idUser == u.idUser;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.idUser;
    }

    public boolean hasBorrowed(User owner) {
        EvalDAO evalDAO = EvalDAO.getInstance();
        return evalDAO.hasBorrowed(owner, this);
    }

    public int getFlorans() {
        return florans;
    }

    public void setFlorans(int florans) {
        this.florans = florans;
    }
}