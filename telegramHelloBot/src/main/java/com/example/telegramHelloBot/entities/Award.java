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
@Entity(name = "awards")
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "award_id")
    int awardId;
    @Column(name = "quest_id")
    int questId;
    @Column(name = "first_part")
    int firstPart;
    @Column(name = "second_part")
    int secondPart;
    @Column(name = "award_content")
    String content;
}
