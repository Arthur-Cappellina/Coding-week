package eu.telecomnancy.codingweek.Models;

public class BasicUserCreator implements UserCreator {
    /**
     * Create an instance of BasicUser.
     * @return User
     */
    @Override
    public User createUser() {
        return new BasicUser();
    }

    /**
     * Create an instance of BasicUser with parameters.
     * @param firstName
     * @param lastName
     * @param zipCode
     * @param postalAddress
     * @param email
     * @param password
     * @return User
     */
    @Override
    public User createUser(String firstName, String lastName, String zipCode, String postalAddress, String city, String email, String password) {
        return new BasicUser(firstName, lastName, zipCode, postalAddress, city, email, password);
    }
}
