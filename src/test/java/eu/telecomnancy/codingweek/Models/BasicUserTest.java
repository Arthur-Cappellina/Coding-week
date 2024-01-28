package eu.telecomnancy.codingweek.Models;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

class BasicUserTest {

    @Test
    void addProposal() {
        Proposal p1 = new Product();
        Proposal p2 = new Product();
        BasicUser basicUser = new BasicUser();
        basicUser.addProposal(p1);
        Assertions.assertEquals(1, basicUser.getProposals().size());
        Assertions.assertTrue(basicUser.getProposals().contains(p1));
        basicUser.addProposal(p2);
        Assertions.assertEquals(2, basicUser.getProposals().size());
        Assertions.assertTrue(basicUser.getProposals().contains(p1));
        Assertions.assertTrue(basicUser.getProposals().contains(p2));
    }

    @Test
    void removeProposal() {
        Proposal p1 = new Product();
        Proposal p2 = new Product();
        BasicUser basicUser = new BasicUser();
        basicUser.addProposal(p1);
        basicUser.addProposal(p2);
        Assertions.assertEquals(2, basicUser.getProposals().size());

        basicUser.removeProposal(p2);
        Assertions.assertEquals(1, basicUser.getProposals().size());
        Assertions.assertTrue(basicUser.getProposals().contains(p1));
        Assertions.assertFalse(basicUser.getProposals().contains(p2));

        basicUser.removeProposal(p1);
        Assertions.assertEquals(0, basicUser.getProposals().size());
        Assertions.assertFalse(basicUser.getProposals().contains(p1));
        Assertions.assertFalse(basicUser.getProposals().contains(p2));
    }
}