package eu.telecomnancy.codingweek.Models;

import java.util.ArrayList;
import java.util.Objects;

public class Planning {
    private ArrayList<RegularWeekly> days;

    public Planning(){
        this.days = new ArrayList<>();
    }

    public ArrayList<RegularWeekly> getDays() {
        return days;
    }

    public void addDay(RegularWeekly rg){
        days.add(rg);
    }

    public void removeDay(String jour){
        for (RegularWeekly d : days){
            if (Objects.equals(d.getJour(), jour)){
                days.remove(d);
            }
        }
    }
}
