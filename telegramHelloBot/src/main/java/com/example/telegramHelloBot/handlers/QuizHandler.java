package com.example.telegramHelloBot.handlers;

import com.example.telegramHelloBot.entities.*;
import com.example.telegramHelloBot.repositories.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.telegramHelloBot.utils.TelegramUtil.*;

@Component
@AllArgsConstructor
public class QuizHandler {
    public static final String QUIZ_WELCOME = "/start";
    public static final String QUIZ_START = "Начать викторину";
    public static final String QUIZ_RESTART = "Пройти викторину ещё раз";
    public static final String QUIZ_NEXT_QUESTION = "Следующий вопрос";

    private final String WELCOME_TEXT="Приветствуем Вас на нашем болоте.\uD83D\uDC4B\n" +
            "\n" +
            "Уверены, Вам нравится мультик о страшном огре, прекрасной принцессе и говорливом осле.Возможно, Вы даже пересматривали его несколько раз. Но достойны ли Вы считать себя настоящим шрекспертом?\uD83E\uDD14\n" +
            "\n" +
            "Мы приглашаем Вас принять участие в нашей викторине, чтобы узнать это. Ответьте на все наши вопросы и получите восхитительные изображения в высоком качестве с главным героем этого мультфильма.\uD83C\uDFC6\uD83E\uDD29\uD83C\uDFC5";

    private final UserRepository userRepository;
    private final QuestRepository questRepository;
    private final OptionRepository optionRepository;
    private final ReplyRepository replyRepository;
    private final AwardRepository awardRepository;

    public List<PartialBotApiMethod<?>> handle(String chatId, String message) throws IOException {
        List<PartialBotApiMethod<?>> partialBotApiMethodList=new ArrayList<>();
        switch (message) {
            case QUIZ_WELCOME -> partialBotApiMethodList=sayHello(chatId);
            case QUIZ_START -> partialBotApiMethodList=startNewQuiz(chatId);
            case QUIZ_RESTART -> partialBotApiMethodList=startNewQuiz(chatId);
            case QUIZ_NEXT_QUESTION -> partialBotApiMethodList=nextQuestion(chatId,false);
            default -> partialBotApiMethodList=checkAnswer(chatId,message);
        }
        return partialBotApiMethodList;
    }

    private List<PartialBotApiMethod<?>> sayHello(String chatId) throws IOException {
        User user = userRepository.findById(Integer.parseInt(chatId))
                .orElse(new User(Integer.parseInt(chatId), 1, 0, 0));
        user.setQuestId(1);
        userRepository.save(user);
        List<PartialBotApiMethod<?>> partialBotApiMethodList=new ArrayList<>();
        SendAnimation helloGif=createGif(chatId);
        SendMessage helloText=createMessage(chatId,WELCOME_TEXT);
        helloText.setReplyMarkup(createReplyKeyboard(QUIZ_START));
        partialBotApiMethodList.add(helloGif);
        partialBotApiMethodList.add(helloText);
        return partialBotApiMethodList;
    }

    private List<PartialBotApiMethod<?>> startNewQuiz(String chatId) throws IOException {
        return nextQuestion(chatId,true);
    }

    private List<PartialBotApiMethod<?>> nextQuestion(String chatId,boolean isStart) throws IOException {
        User user = userRepository.findById(Integer.parseInt(chatId))
                .orElse(new User(Integer.parseInt(chatId), 1, 0, 0));
        if(isStart){
            user.setQuestId(0);
            user.setCurrentScore(0);
        }
        Quest quest = questRepository.findById(user.getQuestId()%10+1).orElse(new Quest());
        user.setQuestId(user.getQuestId() + 1);
        userRepository.save(user);
        SendPhoto question = createPhotoByUrl(chatId, quest.getImage());
        List<String> options = quest.getOptions().stream().map(Option::getText).toList();
        ReplyKeyboard replyKeyboard = createReplyKeyboard(options);
        question.setCaption(quest.getText());
        question.setReplyMarkup(replyKeyboard);
        return List.of(question);
    }

    private List<PartialBotApiMethod<?>> checkAnswer(String chatId,String userAnswer) throws IOException {
        List<PartialBotApiMethod<?>> conclusion=new ArrayList<>();
        User user = userRepository.findById(Integer.parseInt(chatId))
                .orElse(new User(Integer.parseInt(chatId), 1, 0, 0));
        Option option=optionRepository.findByQuestIdAndIsCorrect(user.getQuestId(),true);
        List<Reply> replies=replyRepository.findByQuestIdAndIsCorrectOrderByPositionAsc(user.getQuestId(),option.getText().equals(userAnswer));
        boolean keybordFlag=false;
        String keyboardText=QUIZ_NEXT_QUESTION;
        if(user.getQuestId()>=10) keyboardText=QUIZ_RESTART;
        for(Reply reply:replies){
            if(reply.isText()){
                SendMessage message=createMessage(chatId,reply.getContent());
                if(!keybordFlag){
                    ReplyKeyboard keyboard=createReplyKeyboard(keyboardText);
                    message.setReplyMarkup(keyboard);
                }
                conclusion.add(message);
            }
            else{
                SendPhoto photo=createPhotoByUrl(chatId,reply.getContent());
                if(!keybordFlag){
                    ReplyKeyboard keyboard=createReplyKeyboard(keyboardText);
                    photo.setReplyMarkup(keyboard);
                }
                conclusion.add(photo);
            }
        }
        if(option.getText().equals(userAnswer)){
            List<Award> awards=awardRepository.findByQuestId(user.getQuestId());
            Award award=awards.getFirst();
            int awardChance=new Random().nextInt(100)+1;
            for(Award el:awards){
                if(awardChance>=el.getFirstPart()&&awardChance<= el.getSecondPart()) {
                    award = el;
                    break;
                }
            }
            SendPhoto photo=createPhotoByUrl(chatId,award.getContent());
            conclusion.add(photo);
            user.setCurrentScore(user.getCurrentScore()+1);
            user.setMaxScore(Math.max(user.getMaxScore(), user.getCurrentScore()));
            userRepository.save(user);
        }
        return conclusion;
    }

}
