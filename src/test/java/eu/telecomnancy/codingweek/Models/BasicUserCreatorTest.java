package eu.telecomnancy.codingweek.Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicUserCreatorTest {

    @Test
    void createUser1() {
        BasicUserCreator BasicUserCreator = new BasicUserCreator();
        BasicUser basic = (BasicUser) BasicUserCreator.createUser();
        Assertions.assertTrue(basic instanceof BasicUser);
    }

    @Test
    void createUser2() {
        BasicUserCreator BasicUserCreator = new BasicUserCreator();
        BasicUser basic = (BasicUser) BasicUserCreator.createUser("first", "last", "51100", "10 rue Hasard", "Nancy", "email@email.com", "pa$sw0rd");
        Assertions.assertTrue(basic instanceof BasicUser);
        Assertions.assertEquals("first", basic.getFirstName());
        Assertions.assertEquals("last", basic.getLastName());
        Assertions.assertEquals("51100", basic.getZipCode());
        Assertions.assertEquals("10 rue Hasard", basic.getPostalAddress());
        Assertions.assertEquals("Nancy", basic.getCity());
        Assertions.assertEquals("email@email.com", basic.getEmail());
        Assertions.assertEquals("pa$sw0rd", basic.getPassword());
    }
}