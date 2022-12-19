package com.example.HC_SHARNIR_BOT.service.impl;

import com.example.HC_SHARNIR_BOT.repository.AdsRepository;
import com.example.HC_SHARNIR_BOT.repository.UserRepository;
import com.example.HC_SHARNIR_BOT.service.RegisterService;
import com.example.HC_SHARNIR_BOT.utils.Buttons;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Getter
@EnableScheduling
public class RegisterServiceImpl implements RegisterService {
    private final MessageServiceImpl messageService;
    private final AdsRepository adsRepository;
    private final Buttons buttons;
    private final String GROUP_ID = "-1001387017494";
    private Set<String> plus = new HashSet<>();
    private Set<String> minus = new HashSet<>();
    private final Long GEN_ID = 1L;
    private final UserRepository userRepository;

    public RegisterServiceImpl(MessageServiceImpl messageService, AdsRepository adsRepository, Buttons buttons, UserRepository userRepository) {
        this.messageService = messageService;
        this.adsRepository = adsRepository;
        this.buttons = buttons;
        this.userRepository = userRepository;
    }

    @Override
    public void registerPlayerOnGame(Update update) {
        setDay(update, GEN_ID);
    }

    private void setDay(Update update, long day) {
        String title = adsRepository.findById(day).get().getMsg() + "\n";
        String callbackData = update.getCallbackQuery().getData();
        String fullName = update.getCallbackQuery().getFrom().getLastName() + " " + update.getCallbackQuery().getFrom().getFirstName();
        if (fullName.equals("Sobolev Den")) {
            fullName = "Соболев Денис";
        }
        if (fullName.equals("Lobastov Kirill")) {
            fullName = "Лобастов Кирилл";
        }
        if (fullName.equals("null Alexey")) {
            fullName = "Лёха";
        }
        if (fullName.equals("null Дмитрий")) {
            fullName = "Гитюк Дима";
        }
        if (fullName.equals("Mezentsev Mark")) {
            fullName = "Мезенцев Марк";
        }
        if (fullName.equals("null Danya Chel")) {
            fullName = "Лаптев Данил";
        }
        if (fullName.equals("Vigandt Artem")) {
            fullName = "Вигандт Артём";
        }
        if (fullName.equals("Gu Ma")) {
            fullName = "Чистов Никита";
        }
        if (fullName.equals("Jorge Jorge")) {
            fullName = "Кузнецов Гоша";
        }
        if (fullName.equals("Podzhiganov Andrey")) {
            fullName = "Поджиганов Андрей";
        }
        if (fullName.equals("null Dmitriy")) {
            fullName = "Звягинцев Дима";
        }
        if (fullName.equals("null Никита")) {
            fullName = "Нациевский Никита";
        }
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        if (callbackData.equals(buttons.getYES())) {
            String peoplePlus = "Проголосовал за '+' - " + update.getCallbackQuery().getFrom().getFirstName() + " " + update.getCallbackQuery().getFrom().getUserName() + "\n";
            plus.add(fullName);
            if (minus.contains(update.getCallbackQuery().getFrom().getLastName() + " " + update.getCallbackQuery().getFrom().getFirstName())) {
                minus.remove(update.getCallbackQuery().getFrom().getLastName() + " " + update.getCallbackQuery().getFrom().getFirstName());
                log.info("remove player from map minus");
            }
            log.info("добавил игрока в мапу плюсов" + update.getCallbackQuery().getFrom().getFirstName() + " " + update.getCallbackQuery().getFrom().getId());

            messageService.sendEditMessageWithMarkup((title + peoplePlus + textTitle()), chatId, messageId);
        } else if (callbackData.equals(buttons.getNO())) {
            String peopleMinus = "Проголосовал за '-' - " + update.getCallbackQuery().getFrom().getFirstName() + " " + update.getCallbackQuery().getFrom().getUserName() + "\n";
            minus.add(fullName);
            if (plus.contains(update.getCallbackQuery().getFrom().getLastName() + " " + update.getCallbackQuery().getFrom().getFirstName())) {
                plus.remove(update.getCallbackQuery().getFrom().getLastName() + " " + update.getCallbackQuery().getFrom().getFirstName());
                log.info("remove player from map plus");
            }
            log.info("добавил игрока в мапу минусов" + update.getCallbackQuery().getFrom().getFirstName());
            messageService.sendEditMessageWithMarkup((title + peopleMinus + textTitle()), chatId, messageId);
        }
    }

    public void sendListPlayers() {
        SendMessage list = new SendMessage();
        list.setText(playersOnGame());
        list.setChatId(GROUP_ID);
        messageService.sendSimpleMsg(list);
    }

    public String playersOnGame() {
        String name;
        List<String> p = List.copyOf(plus);
        List<String> players = new ArrayList<>();
        for (String pl : p) {
            name = pl + "\n";
            players.add(name);
        }
        return String.valueOf(players).replaceAll("\\p{P}", "");
    }

    public String textTitle() {
        String txt = "+ " + plus.size() + ":\n" + getPlus().toString().replaceAll("[^\\p{L}\\p{N}]+", " ") + "\n " +
                "- " + minus.size() + ":\n" + getMinus().toString().replaceAll("[^\\p{L}\\p{N}]+", " ");
        return txt;
    }
}
