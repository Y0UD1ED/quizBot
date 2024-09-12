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
@Entity(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    int id;
    @ManyToOne
    @JoinColumn(name = "quest_id")
    Quest quest;
    @Column(name = "option_text")
    String text;
    @Column(name = "iscorrect")
    boolean isCorrect;
}
