package eu.telecomnancy.codingweek.Models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegularWeekly extends Disponibility {
    private static List<String> days;
    private String jour;


    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public void initDays(){
        days = new ArrayList<>();
        days.add("MONDAY");
        days.add("TUESDAY");
        days.add("WEDNESDAY");
        days.add("THURSDAY");
        days.add("FRIDAY");
        days.add("SATURDAY");
        days.add("SUNDAY");
    }

    public RegularWeekly(String jour){
        super();
        this.idDisponibility = -1;
        this.jour = jour;
        this.initDays();
    }

    public RegularWeekly(int idDisponibility, String jour){
        super();
        this.idDisponibility = idDisponibility;
        this.jour = jour;
        this.initDays();
    }

    private static boolean estEcartDe7JoursAuPlus(LocalDateTime date1, LocalDateTime date2) {
        long heuresEntre = ChronoUnit.HOURS.between(date1, date2);
        return Math.abs(heuresEntre) >= 7 * 24;
    }
    @Override
    public boolean isDisponible(LocalDateTime dateStart, LocalDateTime dateEnd) {
        if (estEcartDe7JoursAuPlus(dateStart,dateEnd)){
            return true;
        }
        String dofs = dateStart.getDayOfWeek().toString();
        String dofe = dateEnd.getDayOfWeek().toString();
        int is = days.indexOf(dofs);
        int ie = days.indexOf(dofe);
        int i = days.indexOf(jour);
        if (is<=ie){
            return is <= i && i <= ie;
        }
        else {
            return is>=i && i>=ie;
        }
    }

    @Override
    public String getDisponibility() {
        return "Disponible tous les : " + jour;
    }


}
