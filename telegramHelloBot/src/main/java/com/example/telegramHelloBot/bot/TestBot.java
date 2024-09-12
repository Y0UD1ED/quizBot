package com.example.telegramHelloBot.bot;

import com.example.telegramHelloBot.handlers.QuizHandler;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Component
@Slf4j
public class TestBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botUsername;
    @Value("7486111790:AAF8H9asvwLdcTwEUGXrYOEROcKUyCb6Sfo")
    String botToken;
    private final QuizHandler quizHandler;
    public TestBot(@Value("7486111790:AAF8H9asvwLdcTwEUGXrYOEROcKUyCb6Sfo") String botToken,QuizHandler quizHandler){
        super(botToken);
        this.quizHandler=quizHandler;
    }
    @Override
    public void onUpdateReceived(Update update) {

        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                List<PartialBotApiMethod<?>> list = quizHandler.handle(String.valueOf(update.getMessage().getChatId()), update.getMessage().getText());
                if(list!=null &&!list.isEmpty()){
                    for (PartialBotApiMethod el:list) {
                        executeWithExceptionCheck(el);
                        Thread.sleep(1000);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void executeWithExceptionCheck(PartialBotApiMethod<?> method) {
        try {
            String textMethod = method.getMethod();
            switch (textMethod) {
                case "sendAnimation" -> execute((SendAnimation) method);
                case "sendmessage" -> execute((SendMessage) method);
                case "sendphoto" -> execute((SendPhoto) method);
                default -> log.error("unknown method");
            }
        } catch (TelegramApiException e) {
            log.error("oops");
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
