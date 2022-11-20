package com.example.HC_SHARNIR_BOT.service.impl;

import com.example.HC_SHARNIR_BOT.service.NotificationsService;
import com.example.HC_SHARNIR_BOT.utils.Emojis;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class NotificationsServiceImpl implements NotificationsService {
    private final MessageServiceImpl messageService;
    private final String GROUP_ID = "-1001387017494";

    public NotificationsServiceImpl(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    @Override
    public void sendNotification() {
        SendMessage msg = new SendMessage();
        msg.setText("Парни, деньги скидываем, не стесняемся, сумма та же, правила те же " + Emojis.COPYRIGHT);
        msg.setChatId(GROUP_ID);
        messageService.sendSimpleMsg(msg);
    }
}
