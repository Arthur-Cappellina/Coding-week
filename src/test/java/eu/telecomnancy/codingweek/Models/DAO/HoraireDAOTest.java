package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Models.BasicUser;
import eu.telecomnancy.codingweek.Models.Horaire;
import eu.telecomnancy.codingweek.Models.Service;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

class HoraireDAOTest {

    @Test
    void test1() throws ParseException {
        BasicUser b = new BasicUser("first", "last", "zip", "postal", "city", "email101@email.com", "pass");
        Service service = new Service("service", "desc", "picture", b, 8, 1);
        Horaire h = new Horaire("10-11-2022", "12-11-2022");

        BasicUserDAO.getInstance().insertBasicUser(b);
        ServiceDAO.getInstance().insertService(service);
        HoraireDAO.getInstance().insertHoraire(h, service.getIdProposal());

        HoraireDAO.getInstance().deleteHoraire(h);
        ServiceDAO.getInstance().deleteService(service);
        BasicUserDAO.getInstance().deleteBasicUser(b);
    }

}