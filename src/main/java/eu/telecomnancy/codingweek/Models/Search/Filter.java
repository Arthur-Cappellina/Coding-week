package eu.telecomnancy.codingweek.Models.Search;

import eu.telecomnancy.codingweek.Models.Proposal;
import java.util.List;

public abstract class Filter implements Search{

    protected Search searchInstance;

    public Filter(Search searchInstance) {
        this.searchInstance = searchInstance;
    }
    public abstract List<Proposal> filtering(List<Proposal> proposals);
}
