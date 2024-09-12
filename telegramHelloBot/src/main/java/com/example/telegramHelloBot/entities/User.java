package com.example.telegramHelloBot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    int userId;
    @Column(name = "quest_id")
    int questId;
    @Column(name = "max_score")
    int maxScore;
    @Column(name="curr_score")
    int currentScore;

}
