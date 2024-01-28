package eu.telecomnancy.codingweek.Models.Search;

import eu.telecomnancy.codingweek.Models.Disponibility;
import eu.telecomnancy.codingweek.Models.Proposal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DateFilter extends Filter {

    private LocalDateTime start;
    private LocalDateTime end;

    public DateFilter(Search searchInstance, LocalDateTime start, LocalDateTime end) {
        super(searchInstance);
        this.start = start;
        this.end = end;
    }

    public List<Proposal> filtering(List<Proposal> proposals) {
        List<Proposal> res = new ArrayList<>();
        List<Proposal> result = super.searchInstance.filtering(proposals);
        for (Proposal proposal : proposals) {
            System.out.println("Proposal : " + proposal);
            if(proposal.getDisponibilities() == null) continue;
            for (Disponibility disponibility : proposal.getDisponibilities()) {
                if (disponibility.isDisponible(start, end)) {
                    res.add(proposal);
                    break;
                }
            }
        }
        System.out.println("DateFilter : " + result.size() + " proposals");
        return res;
    }
}
