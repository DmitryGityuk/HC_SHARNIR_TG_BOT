package com.example.HC_SHARNIR_BOT.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface MessageService {

    void sendSimpleMsg(SendMessage msg);

    void sendMessage(long chatId, String textToSend);

    void prepareAndSendMessage(long chatId, String textToSend);

    void sendWelcomeMessage(long chatId, String name);

    void sendHelpMessage(long chatId, String name);

    void sendMessageRegisterOnGame();

    void sendEditMessageWithMarkup(String text, long chatId, long messageId);

}
