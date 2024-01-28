package eu.telecomnancy.codingweek.Models;

import lombok.Setter;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Proposal {
    private int idProposal;
    @Setter
    private String title;

    @Setter
    private String description;

    @Setter
    private String picture;

    private int dailyCost;

    @Setter
    private List<Disponibility> disponibilities;

    @Setter
    private User user;

    private int id;

    private int floransCost;

    public Proposal(String title,String description, String picture, int coutJournalier, User user, int id) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.user = user;
        this.disponibilities = new ArrayList<>();
        this.dailyCost = coutJournalier;
        this.id = id;
    }

    public Proposal(String title,String description, String picture,User user){
             this.idProposal = -1;
             this.title = title;
             this.description = description;
             this.picture = picture;
             this.user = user;
    }

    public Proposal(int idProposal, String title, String description, String picture, User user){
        this.idProposal = idProposal;
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.user = user;
    }
    public Proposal(){
        this.disponibilities = new ArrayList<>();
    }

    public void addDisponibility(Disponibility disponibility){
        disponibilities.add(disponibility);
    }

    public List<Disponibility> getDisponibilities() {
        return disponibilities;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    public int getId() {
    	return this.id;
    }

    public void setDisponibilities(List<Disponibility> disponibilities) {
        this.disponibilities = disponibilities;
    }

    public int getDailyCost() {
        return floransCost;
    }


    public int getIdProposal() {
        return idProposal;
    }

    public void setIdProposal(int idProposal) {
        this.idProposal = idProposal;
    }

    public boolean isProposalDisponible(LocalDateTime startDate, LocalDateTime endDate) {
        boolean res = true;
        for (Disponibility d : this.disponibilities) {
            if (!d.isDisponible(startDate, endDate)) {
                res = false;
            }
        }
        return res;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setDailyCost(int dailyCost) {
        this.dailyCost = dailyCost;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFloransCost() {
    	return this.floransCost;
    }

    public void setFloransCost(int floransCost) {
    	this.floransCost = floransCost;
    }
}
