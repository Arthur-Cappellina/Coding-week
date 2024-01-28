package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Models.BasicUser;
import eu.telecomnancy.codingweek.Models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class ProductDAOTest {

    @Test
    void test1() throws SQLException {
        BasicUser b = new BasicUser("first", "last", "zip", "postal", "city", "email101@email.com", "pass");
        BasicUserDAO.getInstance().insertBasicUser(b);
        BasicUser b2 = new BasicUser("first2", "last", "zip", "postal", "city", "email11@email.com", "pass");
        BasicUserDAO.getInstance().insertBasicUser(b2);

        Product p = new Product("title", "description", "picture", b, 1, 1);
        ProductDAO.getInstance().insertProduct(p);

        Product pbd = ProductDAO.getInstance().getProductWithId(p.getIdProposal());
        Assertions.assertEquals("title", pbd.getTitle());

        BasicUserDAO.getInstance().deleteBasicUser(b);
        ProductDAO.getInstance().deleteProduct(p);
        BasicUserDAO.getInstance().deleteBasicUser(b2);
    }
}