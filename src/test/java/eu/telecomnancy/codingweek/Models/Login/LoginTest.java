package eu.telecomnancy.codingweek.Models.Login;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import eu.telecomnancy.codingweek.Models.*;

public class LoginTest {
    @Test
    public void test1() {
        Login login = new Login("email", "password");
        assertEquals("email", login.getEmail());
        assertEquals("password", login.getPassword());
    }
    @Test
    public void test2() {
        Login login = new Login("email", "password");
        assertTrue(login.isValid());
    }
    @Test
    public void test3() {
        Registration registration = new Registration();
        assertFalse(registration.isValid());
        registration.setEmail("email3@email.com");
        registration.setPassword("password");
        registration.setPasswordConfirmation("password");
        registration.setFirstName("first");
        registration.setLastName("last");
        registration.setZipCode("54500");
        registration.setPostalAddress("1 Impasse des Lilas");
        registration.setCity("Nancy");

        BasicUser user = registration.signup();

        Login login = new Login("email3@email.com", "password");
        assertTrue(login.isValid());
        assertNotNull(login.login());
    }
}
