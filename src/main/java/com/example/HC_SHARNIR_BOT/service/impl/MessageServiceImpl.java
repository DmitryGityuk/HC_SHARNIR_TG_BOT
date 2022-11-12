package com.example.HC_SHARNIR_BOT.service.impl;

import com.example.HC_SHARNIR_BOT.handler.impl.MessageHandlerSenderImpl;
import com.example.HC_SHARNIR_BOT.model.User;
import com.example.HC_SHARNIR_BOT.repository.AdsRepository;
import com.example.HC_SHARNIR_BOT.repository.UserRepository;
import com.example.HC_SHARNIR_BOT.service.MessageService;
import com.example.HC_SHARNIR_BOT.utils.Buttons;
import com.example.HC_SHARNIR_BOT.utils.Emojis;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final Buttons buttons;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final MessageHandlerSenderImpl messageHandler;
    private final Long MON_ID = 1L;
    private final Long THU_ID = 2L;

    public MessageServiceImpl(Buttons buttons, AdsRepository ads, UserRepository userRepository, MessageHandlerSenderImpl messageHandler) {
        this.buttons = buttons;
        this.adsRepository = ads;
        this.userRepository = userRepository;
        this.messageHandler = messageHandler;
    }

    @Override
    public void sendSimpleMsg(SendMessage msg) {
        messageHandler.executeMessage(msg);
    }

    @Override
    public void sendMessage(long chatId, String textToSend) {
        messageHandler.sendMsgWithMarkup(chatId, textToSend);
    }

    @Override
    public void prepareAndSendMessage(long chatId, String textToSend) {
        messageHandler.sendMsg(chatId, textToSend);
    }

    @Override
    public void sendEditMessage(String text, long chatId, long messageId) {
        messageHandler.executeEditMessage(text, chatId, messageId);
    }

    @Override
    public void sendWelcomeMessage(long chatId, String name) {
        String welcomeAnswer = EmojiParser.parseToUnicode(
                "Привет, " + name + ", рад что ты присоединился."
                        + "\nДля начала нажми на кнопку 'Регистрация' внизу чтобы я добавил тебя в базу игроков" + Emojis.POINT_DOWN
                        + "Или просто напиши 'Регистрация' в чате."
                        + "\nДля того чтобы посмотреть что я умею нажми на копку 'Помощь' либо вызови команду \n'/help'"
                        + "\nКаждую среду и воскресенье я буду делать рассылку с опросом об участии в предстоящем туре."
                        + "\nP.S если ты обнаружишь какие-либо неточности/баги, то не спеши закидывать меня ссаными тряпками" + Emojis.MOYAI + ", а лучше напиши Диме, он по идее должен поправить ситуацию."
                        + " Ну и по всем предложениям тоже можешь ему писать " + Emojis.CALLING);
        log.info("Replied to user " + name);
        sendMessage(chatId, welcomeAnswer);
    }

    @Override
    public void sendHelpMessage(long chatId, String name) {
        String helpAnswer = EmojiParser.parseToUnicode(
                name + ", чем тебе помочь?"
                        + "Кнопка 'Регистрация' добавит тебя в базу игроков этого сезона"
                        + "\nНажав на кнопку 'Про меня' или вызвав команду '/mydata' покажу тебе твою статистику." + Emojis.CHART_WITH_UPWARDS_TREND
                        + "\nКоманда '/topscorers' и кнопка 'Голы' покажет 3 лучших бомбардиров."
                        + "\nКоманда '/topassists' и кнопка 'Голевые пасы' покажет 3 лучших распасовщиков."
                        + "\nКоманда '/topcomb' и кнопка 'Голы + Пасы' покажет 3 лучших игроков."
                        + "\nКоманда '/allplayers' и кнопка 'Таблица' покажет всех игроков."
                        + "\nКоманда '/help' и кнопка 'Помощь' покажет тебе это сообщение."
        );
        log.info("Helping to user " + name);
        prepareAndSendMessage(chatId, helpAnswer);
    }

    @Override
    public void sendMessageRegisterOnMondayGame() {
        messageHandler.executeMessage(setMessage(MON_ID));
    }

    @Override
    public void sendMessageRegisterOnThursdayGame() {
        messageHandler.executeMessage(setMessage(THU_ID));
    }

    private SendMessage setMessage(long id) {
        var users = userRepository.findAll();
        SendMessage msg = new SendMessage();
        msg.setReplyMarkup(buttons.getInlineKeyboardMarkup());
        for (User user : users) {
            msg.setText(adsRepository.findById(id).get().getMsg());
            msg.setChatId(String.valueOf(user.getChatId()));
        }
        return msg;
    }

}
