package com.example.telegramHelloBot.configs;

import com.example.telegramHelloBot.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan
@EntityScan
@Configuration
public class AppConfig {

}
