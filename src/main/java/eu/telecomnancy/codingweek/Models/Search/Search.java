package eu.telecomnancy.codingweek.Models.Search;

import eu.telecomnancy.codingweek.Models.Disponibility;
import eu.telecomnancy.codingweek.Models.Proposal;

import java.time.LocalDateTime;
import eu.telecomnancy.codingweek.Models.Horaire;

import java.util.List;

public interface Search {
    public List<Proposal> filtering(List<Proposal> proposals);
}
