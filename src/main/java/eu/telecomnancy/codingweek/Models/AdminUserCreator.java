package eu.telecomnancy.codingweek.Models;

public class AdminUserCreator implements UserCreator {
    /**
     * Create an instance of Admin.
     * @return
     */
    @Override
    public User createUser() {
        return new Admin();
    }

    /**
     * Create an instance of Admin with parameters.
     * @param firstName
     * @param lastName
     * @param zipCode
     * @param postalAddress
     * @param email
     * @param password
     * @return
     */
    @Override
    public User createUser(String firstName, String lastName, String zipCode, String postalAddress, String city, String email, String password) {
        return new Admin(firstName, lastName, zipCode, postalAddress, city, email, password);
    }
}
