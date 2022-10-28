package com.example.HC_SHARNIR_BOT.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface MessageService {

    SendMessage sendMessage(long chatId, String textToSend);

    SendMessage prepareAndSendMessage(long chatId, String textToSend);

    SendMessage getWelcomeMessage(long chatId, String name);

    SendMessage getHelpMessage(long chatId, String name);

}
