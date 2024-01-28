package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Models.BasicUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class BasicUserDAOTest {

    @Test
    void test1() throws SQLException {
        BasicUser b1 = new BasicUser("first", "last", "zip", "address", "city", "email@email.com", "password");
        boolean test = BasicUserDAO.getInstance().insertBasicUser(b1);
        Assertions.assertTrue(test);
        BasicUser b = BasicUserDAO.getInstance().getBasicUserWithEmail("email@email.com");
        Assertions.assertEquals("first", b.getFirstName());
        Assertions.assertEquals("last", b.getLastName());
        Assertions.assertEquals("zip", b.getZipCode());
        Assertions.assertEquals("address", b.getPostalAddress());
        Assertions.assertEquals("city", b.getCity());
        Assertions.assertEquals("email@email.com", b.getEmail());
        Assertions.assertEquals("password", b.getPassword());
        boolean test2 = BasicUserDAO.getInstance().deleteBasicUser(b);
        Assertions.assertTrue(test2);
        Assertions.assertNull(BasicUserDAO.getInstance().getBasicUserWithEmail("email@email.com"));
    }
}