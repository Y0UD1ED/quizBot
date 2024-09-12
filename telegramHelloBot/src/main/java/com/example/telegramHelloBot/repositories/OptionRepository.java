package com.example.telegramHelloBot.repositories;

import com.example.telegramHelloBot.entities.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option,Integer> {
    Option findByQuestIdAndIsCorrect(int questId,boolean isCorrect);
}
