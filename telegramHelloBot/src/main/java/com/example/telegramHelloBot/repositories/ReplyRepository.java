package com.example.telegramHelloBot.repositories;

import com.example.telegramHelloBot.entities.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply,Integer> {
    List<Reply> findByQuestIdAndIsCorrectOrderByPositionAsc(int questId,boolean isCorrect);
}
