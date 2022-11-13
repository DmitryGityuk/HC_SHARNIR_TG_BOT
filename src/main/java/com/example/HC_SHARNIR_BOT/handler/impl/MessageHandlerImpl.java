package com.example.HC_SHARNIR_BOT.handler.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class MessageHandlerImpl extends TelegramLongPollingBot {

    public MessageHandlerImpl() {

    public MessageHandlerImpl(BotConfig config) {
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

    @Override
    public String getBotUsername() {
        return "hc-sharnir-bot";
    }

    @Override
    public String getBotToken() {
        return "5570132616:AAG1EYgo7_InaLmroyfwkI_ALiak7-1m31A";
    }
}
