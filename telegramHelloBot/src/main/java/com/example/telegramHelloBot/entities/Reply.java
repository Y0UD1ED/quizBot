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
@Entity(name = "replies")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    int replyId;
    @Column(name = "quest_id")
    int questId;
    @Column(name = "iscorrect")
    boolean isCorrect;
    @Column(name = "reply_type")
    boolean isText;
    @Column(name = "reply_content")
    String content;
    @Column(name = "reply_pos")
    int position;
}
