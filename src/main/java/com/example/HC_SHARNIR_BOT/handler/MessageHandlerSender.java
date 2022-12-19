package com.example.HC_SHARNIR_BOT.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface MessageHandlerSender {

    void sendMsgWithMarkup(long chatId, String textToSend);

    void sendMsg(long chatId, String textToSend);

    void executeMessage(SendMessage msg);

    void executeEditMessage(String text, long chatId, long messageId);

    void executeEditMessageWithMarkup(String text, long chatId, long messageId);
}
