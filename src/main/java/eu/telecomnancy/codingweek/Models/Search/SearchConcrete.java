package eu.telecomnancy.codingweek.Models.Search;

import eu.telecomnancy.codingweek.Models.Proposal;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SearchConcrete implements Search {
    private String text;

    public SearchConcrete(String text) {
        this.text = text;
    }

    public SearchConcrete() {
        this.text = "";
    }

    public boolean isSearchValid() {
        return this.text != null;
    }

    public String getErrorMessage() {
        if (!this.isSearchValid()) {
            return "Veuillez remplir tous les champs.";
        }
        return "Erreur inconnue.";
    }

    public List<Proposal> filtering(List<Proposal> proposals) {
        List<Proposal> result = new java.util.ArrayList<>();
        if (!this.isSearchValid()) {
            throw new IllegalArgumentException(this.getErrorMessage());
        } else {
            for (Proposal proposal : proposals) {
                if (proposal.getTitle().contains(this.text)) {
                    result.add(proposal);
                }
            }
        }
        return result;
    }
}
