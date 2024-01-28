package eu.telecomnancy.codingweek.Models.Messages;

import eu.telecomnancy.codingweek.Models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class Message {

    private User sender, receiver;

    private String content;

    private LocalDateTime date;

    private int id;

    @Override
    public String toString() {
        return "Message{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", id=" + id +
                '}';
    }
}
