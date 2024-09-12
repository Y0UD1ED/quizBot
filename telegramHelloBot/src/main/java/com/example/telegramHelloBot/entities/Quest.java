package com.example.telegramHelloBot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "quests")
public class Quest {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "quest_id")
    int id;
    @Column(name="quest_text")
    String text;
    @Column(name = "quest_image")
    String image;
    @OneToMany(mappedBy = "quest",fetch = FetchType.EAGER)
    List<Option> options;
}
