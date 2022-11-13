package com.example.HC_SHARNIR_BOT.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name = "playersDataTable")
@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class User {

    @Id
    private Long chatId;

    private Timestamp registeredAt;

    private String fullName;

    private String userName;

    private int goals;

    private int assists;

    private int comb;

    private int countVisits;

    private int countGames;

    private float averageScore;

    @Override
    public String toString() {
        return  " Имя: " + fullName +
                ", голы = " + goals +
                ", пасы = " + assists +
                ", пас + гол = " + comb +
                ", игр = " + countGames +
                ", средний бал = " + averageScore + "\n";
    }
}
