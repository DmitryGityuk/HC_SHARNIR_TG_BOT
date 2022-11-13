package com.example.HC_SHARNIR_BOT.service.impl;

import com.example.HC_SHARNIR_BOT.service.RegisterService;
import com.example.HC_SHARNIR_BOT.utils.Buttons;
import com.example.HC_SHARNIR_BOT.utils.Emojis;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@Getter
@EnableScheduling
public class RegisterServiceImpl implements RegisterService {
    private final MessageServiceImpl messageService;
    private final Buttons buttons;
    private Map<String, String> players;
    private final String GROUP_ID = "-1001387017494";

    public RegisterServiceImpl(MessageServiceImpl messageService, Buttons buttons) {
        this.messageService = messageService;
        this.buttons = buttons;
    }

    @Override
    public void registerPlayerOnGame(Update update) {
        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            if (callbackData.equals(buttons.getYES())) {
                String txt = "Отлично, добавил тебя в список. \nСписок всех игроков я отправлю сегодня в 20:00 в общий чат " + Emojis.SPEECH_BALLOON;
                players = new HashMap<>();
                players.put(update.getCallbackQuery().getFrom().getFirstName(), update.getCallbackQuery().getFrom().getLastName());
                log.info("добавил игрока " + update.getCallbackQuery().getFrom().getFirstName());
                messageService.sendEditMessage(txt, chatId, messageId);
            } else if (callbackData.equals(buttons.getNO())) {
                String txt = "Жаль, ждём тебя на следующей игре";
                messageService.sendEditMessage(txt, chatId, messageId);
            }
        }
    }

    @Scheduled(cron = "${cron.schedulerSunday2}")
    @Scheduled(cron = "${cron.schedulerWednesday2}")
    private void sendListPlayers() {
        SendMessage msg = new SendMessage();
        msg.setText("Список игроков на завтра:\n" + playersOnGame(getPlayers()));
        msg.setChatId(GROUP_ID);
        messageService.sendSimpleMsg(msg);
    }


    public <K, V> String playersOnGame(Map<K, V> players) {
        String firstName = players.keySet().toString().replaceAll("[\\p{Ps}\\p{Pe}]", "");
        String secondName = players.values().toString().replaceAll("[\\p{Ps}\\p{Pe}]", "");
        String fullName = firstName + " " + secondName;
        return fullName;
    }
}
