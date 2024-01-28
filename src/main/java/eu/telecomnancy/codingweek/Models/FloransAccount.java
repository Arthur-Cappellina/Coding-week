package eu.telecomnancy.codingweek.Models;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class FloransAccount {
    private int id;
    private int balance;
    private User user;
    public void addBalance(int amount){
        setBalance(getBalance() + amount);
    }
    public void removeBalance(int amount){
        if (getBalance() - amount < 0){
            throw new IllegalArgumentException("Not enough florans");
        }
        setBalance(getBalance() - amount);
    }
}
