package com.example.HC_SHARNIR_BOT.service.impl;

import com.example.HC_SHARNIR_BOT.model.User;
import com.example.HC_SHARNIR_BOT.repository.AdsRepository;
import com.example.HC_SHARNIR_BOT.repository.UserRepository;
import com.example.HC_SHARNIR_BOT.service.MainMenu;
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
    private final MainMenu mainMenu;
    private final Buttons buttons;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final Long MON_ID = 1L;
    private final Long THU_ID = 2L;

    public MessageServiceImpl(MainMenu mainMenu, Buttons buttons, AdsRepository ads, UserRepository userRepository) {
        this.mainMenu = mainMenu;
        this.buttons = buttons;
        this.adsRepository = ads;
        this.userRepository = userRepository;
    }

    @Override
    public SendMessage sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        message.setReplyMarkup(mainMenu.getMainMenuKeyboard());
        return message;
    }

    @Override
    public SendMessage prepareAndSendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        return message;
    }

    @Override
    public SendMessage getWelcomeMessage(long chatId, String name) {
        String welcomeAnswer = EmojiParser.parseToUnicode(
                "Привет, " + name + ", рад что ты присоединился."
                        + "\nДля начала нажми на кнопку 'Регистрация' внизу чтобы я добавил тебя в базу игроков" + Emojis.POINT_DOWN
                        + "Или просто напиши 'Регистрация' в чате."
                        + "\nДля того чтобы посмотреть что я умею нажми на копку 'Помощь' либо вызови команду \n'/help'"
                        + "\nКаждую среду и воскресенье я буду делать рассылку с опросом об участии в предстоящем туре."
                        + "\nP.S если ты обнаружишь какие-либо неточности/баги, то не спеши закидывать меня ссаными тряпками" + Emojis.MOYAI + ", а лучше напиши Диме, он по идее должен поправить ситуацию."
                        + " Ну и по всем предложениям тоже можешь ему писать " + Emojis.CALLING);
        log.info("Replied to user " + name);
        return prepareAndSendMessage(chatId, welcomeAnswer);
    }

    @Override
    public SendMessage getHelpMessage(long chatId, String name) {
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
        return prepareAndSendMessage(chatId, helpAnswer);
    }

    @Override
    public SendMessage sendMessageRegisterOnMondayGame() {
        return setMessage(MON_ID);
    }

    @Override
    public SendMessage sendMessageRegisterOnThursdayGame() {
        return setMessage(THU_ID);
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
