package com.example.HC_SHARNIR_BOT.service.impl;

import com.example.HC_SHARNIR_BOT.service.NotificationsService;
import com.example.HC_SHARNIR_BOT.utils.Emojis;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class NotificationsServiceImpl implements NotificationsService {
    private final MessageServiceImpl messageService;
    private final String GROUP_ID = "-1001387017494";
    private List<String> payNotifications;


    public NotificationsServiceImpl(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    @Scheduled(cron = "${cron.schedulerMonday}")
    @Scheduled(cron = "${cron.schedulerThursday}")
    @Override
    public void sendNotification() {
        payNotifications = Arrays.asList("Парни, поактивнее скидываем монету, не стесняемся" + Emojis.MOYAI,
                "Парни, деньги скидываем, не стесняемся, сумма та же, правила те же " + Emojis.COPYRIGHT);
        Random rand = new Random();
        String random = payNotifications.get(rand.nextInt(payNotifications.size()));
        SendMessage msg = new SendMessage();
        msg.setText(random);
        msg.setChatId(GROUP_ID);
        messageService.sendSimpleMsg(msg);
    }
}
