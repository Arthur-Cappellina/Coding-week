package eu.telecomnancy.codingweek.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeProposal {
    private String title;
    private String description;
    private int id;

    public HomeProposal(int id, String titre, String description){
        this.title = titre;
        this.description = description;
        this.id = id;
    }

}
