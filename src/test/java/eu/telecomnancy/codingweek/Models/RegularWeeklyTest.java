package eu.telecomnancy.codingweek.Models;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
class RegularWeeklyTest {


    @Test
    void Testweekly() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date start = sdf.parse("02-01-2024");
        Date end = sdf.parse("05-01-2024");
        LocalDateTime ds = LocalDateTime.ofInstant(start.toInstant(), sdf.getTimeZone().toZoneId());
        LocalDateTime de = LocalDateTime.ofInstant(end.toInstant(), sdf.getTimeZone().toZoneId());
        RegularWeekly rg = new RegularWeekly("MONDAY");
        boolean b = rg.isDisponible(ds,de);
        assertFalse(b);
    }
}