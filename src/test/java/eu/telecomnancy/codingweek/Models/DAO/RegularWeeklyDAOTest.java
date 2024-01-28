package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Models.BasicUser;
import eu.telecomnancy.codingweek.Models.RegularWeekly;
import eu.telecomnancy.codingweek.Models.Service;
import org.junit.jupiter.api.Test;

class RegularWeeklyDAOTest {
    @Test
    void test1() {
        BasicUser b = new BasicUser("first", "last", "zip", "postal", "city", "email101@email.com", "pass");
        Service service = new Service("service", "desc", "picture", b, 1, 1);
        RegularWeekly h = new RegularWeekly("MONDAY");

        BasicUserDAO.getInstance().insertBasicUser(b);
        ServiceDAO.getInstance().insertService(service);
        RegularWeeklyDAO.getInstance().insertRegularWeekly(h, service.getIdProposal());

        RegularWeeklyDAO.getInstance().deleteRegularWeekly(h);
        ServiceDAO.getInstance().deleteService(service);
        BasicUserDAO.getInstance().deleteBasicUser(b);
    }
}