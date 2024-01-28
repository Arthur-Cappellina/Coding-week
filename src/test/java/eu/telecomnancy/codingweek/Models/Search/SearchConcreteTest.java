package eu.telecomnancy.codingweek.Models.Search;

import eu.telecomnancy.codingweek.Models.BasicUser;
import eu.telecomnancy.codingweek.Models.Proposal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import eu.telecomnancy.codingweek.Models.Product;
import eu.telecomnancy.codingweek.Models.Service;

public class SearchConcreteTest {
    @Test
    void testSearch() {
        SearchConcrete searchConcrete = new SearchConcrete();

        BasicUser user = new BasicUser("John", "Doe", "75000", "1 Avenue de la République", "Paris", "test@gmail.com", "password");

        List<Proposal> proposals = user.getProposals();
        Product product = new Product("title1", "description", "picture", user, 3, 0);
        Service service = new Service("title2", "description", "picture", user, 4, 0);
        proposals.add(product);
        proposals.add(service);


    }
}
