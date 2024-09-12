package com.example.telegramHelloBot.repositories;

import com.example.telegramHelloBot.entities.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwardRepository extends JpaRepository<Award,Integer> {
    List<Award> findByQuestId(int questId);
}
