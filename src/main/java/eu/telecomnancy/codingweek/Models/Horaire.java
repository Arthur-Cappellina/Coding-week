package eu.telecomnancy.codingweek.Models;

import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
public class Horaire extends Disponibility{
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;

    public Horaire(LocalDateTime dateStart, LocalDateTime dateEnd) {
        super();
        this.idDisponibility = -1;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Horaire(String dateStartString, String dateEndString) throws ParseException {
        super();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(dateStartString);
        Date end = sdf.parse(dateEndString);
        this.idDisponibility = -1;
        this.dateStart = LocalDateTime.ofInstant(start.toInstant(), sdf.getTimeZone().toZoneId());
        this.dateEnd = LocalDateTime.ofInstant(end.toInstant(), sdf.getTimeZone().toZoneId());
    }

    public Horaire(int idDisponibility, String dateStartString, String dateEndString) throws ParseException {
        super();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(dateStartString);
        Date end = sdf.parse(dateEndString);
        this.idDisponibility = idDisponibility;
        this.dateStart = LocalDateTime.ofInstant(start.toInstant(), sdf.getTimeZone().toZoneId());
        this.dateEnd = LocalDateTime.ofInstant(end.toInstant(), sdf.getTimeZone().toZoneId());
    }

    @Override
    public boolean isDisponible(LocalDateTime dateStartSearch, LocalDateTime dateEndSearch){
        if (dateStartSearch.isAfter(dateEnd)){
            return false;
        }
        if (dateEndSearch.isBefore(dateStart)){
            return false;
        }

        return true;
    }

    @Override
    public String getDisponibility(){
        return "disponible du : " + this.getDayStart() + " au : " + this.getDayEnd();
    }


    public String getDayStart() { // renvoie String "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateStart.toLocalDate().atStartOfDay().format(formatter).substring(0,10);
    }
    public String getDayEnd() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateEnd.toLocalDate().atStartOfDay().format(formatter).substring(0,10);
    }

}
