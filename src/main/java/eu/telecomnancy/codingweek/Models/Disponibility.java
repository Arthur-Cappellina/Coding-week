package eu.telecomnancy.codingweek.Models;

import java.time.LocalDateTime;

public abstract class Disponibility {
    protected int idDisponibility;
    public abstract boolean isDisponible(LocalDateTime dateStart, LocalDateTime dateEnd);

    public abstract String getDisponibility();

    public int getIdDisponibility() {
        return idDisponibility;
    }

    public void setIdDisponibility(int idDisponibility) {
        this.idDisponibility = idDisponibility;
    }
}
