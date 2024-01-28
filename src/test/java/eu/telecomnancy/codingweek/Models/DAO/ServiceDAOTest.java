package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Models.BasicUser;
import eu.telecomnancy.codingweek.Models.Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ServiceDAOTest {
    @Test
    void test1() {
        BasicUser b = new BasicUser("first", "last", "zip", "postal", "city", "email101@email.com", "pass");
        BasicUserDAO.getInstance().insertBasicUser(b);
        BasicUser b2 = new BasicUser("first2", "last", "zip", "postal", "city", "email11@email.com", "pass");
        BasicUserDAO.getInstance().insertBasicUser(b2);

        Service p = new Service("title", "description", "picture", b, 1, 1);
        ServiceDAO.getInstance().insertService(p);

        Service pbd = ServiceDAO.getInstance().getServiceWithId(p.getIdProposal());
        Assertions.assertEquals("title", pbd.getTitle());

        BasicUserDAO.getInstance().deleteBasicUser(b);
        ServiceDAO.getInstance().deleteService(p);
        BasicUserDAO.getInstance().deleteBasicUser(b2);
    }

}