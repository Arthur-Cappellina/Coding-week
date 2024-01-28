package eu.telecomnancy.codingweek.Models;

public interface UserCreator {
    /**
     * Create an instance of user's subclasses.
     * @return User
     */
    public User createUser();

    /**
     * Create an instance of user's subclasses.
     * @param firstName
     * @param lastName
     * @param zipCode
     * @param postalAddress
     * @param email
     * @param password
     * @return User
     */
    public User createUser(String firstName, String lastName, String zipCode, String postalAddress, String city, String email, String password);
}
