package eu.telecomnancy.codingweek.Models.DAO;

import eu.telecomnancy.codingweek.Models.BasicUser;
import eu.telecomnancy.codingweek.Models.Loan;
import eu.telecomnancy.codingweek.Models.Product;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

class LoanDAOTest {
    @Test
    void test1() throws ParseException {
        BasicUser borrower = new BasicUser("first", "last", "zip", "postal", "city", "email101@email.com", "pass");

        BasicUser b = new BasicUser("first", "last", "zip", "postal", "city", "email101@email.com", "pass");
        Product p = new Product("title", "description", "picture", b, 1, 1);

        Loan loan = new Loan(borrower, p, "10-12-2022", "12-12-2022", false);

        BasicUserDAO.getInstance().insertBasicUser(borrower);
        BasicUserDAO.getInstance().insertBasicUser(b);
        ProductDAO.getInstance().insertProduct(p);
        LoanDAO.getInstance().insertLoan(loan);

        BasicUserDAO.getInstance().deleteBasicUser(borrower);
        BasicUserDAO.getInstance().deleteBasicUser(b);
        ProductDAO.getInstance().deleteProduct(p);
        LoanDAO.getInstance().deleteLoan(loan);
    }
}