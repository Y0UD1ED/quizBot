package com.example.telegramHelloBot.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TelegramUtil {
    // Создаем шаблон SendMessage с включенным Markdown
    public static SendMessage createMessage(String chatId,String messageText) {
        return new SendMessage(chatId,messageText);
    }

    // Создаем кнопку
    public static ReplyKeyboard createReplyKeyboard(List<String> replies) {
        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowList=new ArrayList<>();
        for (int i = 0; i < replies.size()/2*2; i+=2) {
            KeyboardRow row = new KeyboardRow();
            row.add(replies.get(i));
            row.add(replies.get(i+1));
            keyboardRowList.add(row);
        }
        if(replies.size()%2!=0){
            KeyboardRow row = new KeyboardRow();
            row.add(replies.getLast());
            keyboardRowList.add(row);
        }
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboard createReplyKeyboard(String text) {
        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowList=new ArrayList<>();
        KeyboardRow row=new KeyboardRow();
        row.add(text);
        keyboardRowList.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }
    public static SendPhoto createPhotoByUrl(String chatId,String url) throws IOException {
        InputStream in = new URL(url).openStream();
        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(chatId);
        sendPhotoRequest.setPhoto(new InputFile(in,"tmp"));
        return sendPhotoRequest;
    }

    public static SendAnimation createGif(String chatId) throws IOException {
        File file =new File("C:\\Users\\Artem\\Desktop\\shrekQuiz\\videoplayback.gif.mp4");
        SendAnimation sendAnimation = new SendAnimation();
        sendAnimation.setChatId(chatId);
        sendAnimation.setAnimation(new InputFile(file));
        return sendAnimation;
    }
}
