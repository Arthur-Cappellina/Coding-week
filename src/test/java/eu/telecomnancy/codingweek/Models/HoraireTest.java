package eu.telecomnancy.codingweek.Models;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.text.SimpleDateFormat;
class HoraireTest {


    @Test
    void createHoraire() throws ParseException {
        Horaire horaire = new Horaire("08-01-2024","12-01-2024");
        assertEquals("2024-01-08",horaire.getDayStart());
        assertEquals("2024-01-12",horaire.getDayEnd());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date start = sdf.parse("01-08-2024");
        Date end = sdf.parse("01-01-2025");
        LocalDateTime ds = LocalDateTime.ofInstant(start.toInstant(), sdf.getTimeZone().toZoneId());
        LocalDateTime de = LocalDateTime.ofInstant(end.toInstant(), sdf.getTimeZone().toZoneId());
        boolean b = horaire.isDisponible(ds,de);
        assertFalse(b);
    }
}