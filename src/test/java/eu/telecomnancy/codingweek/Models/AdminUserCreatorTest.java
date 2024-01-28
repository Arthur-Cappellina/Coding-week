package eu.telecomnancy.codingweek.Models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class AdminUserCreatorTest {

    @Test
    void createUser1() {
        AdminUserCreator adminUserCreator = new AdminUserCreator();
        Admin admin = (Admin) adminUserCreator.createUser();
        Assertions.assertTrue(admin instanceof Admin);
    }

    @Test
    void createUser2() {
        AdminUserCreator adminUserCreator = new AdminUserCreator();
        Admin admin = (Admin) adminUserCreator.createUser("first", "last", "51100", "10 rue Hasard", "Nancy", "email@email.com", "pa$sw0rd");
        Assertions.assertTrue(admin instanceof Admin);
        Assertions.assertEquals("first", admin.getFirstName());
        Assertions.assertEquals("last", admin.getLastName());
        Assertions.assertEquals("51100", admin.getZipCode());
        Assertions.assertEquals("10 rue Hasard", admin.getPostalAddress());
        Assertions.assertEquals("Nancy", admin.getCity());
        Assertions.assertEquals("email@email.com", admin.getEmail());
        Assertions.assertEquals("pa$sw0rd", admin.getPassword());
    }
}