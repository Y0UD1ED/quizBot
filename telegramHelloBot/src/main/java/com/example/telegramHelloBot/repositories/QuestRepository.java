package com.example.telegramHelloBot.repositories;

import com.example.telegramHelloBot.entities.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepository extends JpaRepository<Quest,Integer> {
}
