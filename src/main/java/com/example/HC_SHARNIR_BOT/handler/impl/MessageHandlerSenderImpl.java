package com.example.HC_SHARNIR_BOT.handler.impl;

import com.example.HC_SHARNIR_BOT.handler.MessageHandlerSender;
import com.example.HC_SHARNIR_BOT.service.MainMenu;
import com.example.HC_SHARNIR_BOT.utils.Buttons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class MessageHandlerSenderImpl implements MessageHandlerSender {
    private final MainMenu mainMenu;
    private final Buttons buttons;

    public MessageHandlerSenderImpl(MainMenu mainMenu, Buttons buttons) {
        this.mainMenu = mainMenu;
        this.buttons = buttons;
    }

    @Override
    public void sendMsgWithMarkup(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        message.setReplyMarkup(mainMenu.getMainMenuKeyboard());
        executeMessage(message);
    }

    @Override
    public void sendMsg(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        executeMessage(message);
    }

    @Override
    public void executeEditMessage(String text, long chatId, long messageId) {
        EditMessageText msg = new EditMessageText();
        msg.setChatId(String.valueOf(chatId));
        msg.setText(text);
        msg.setMessageId((int) messageId);
        try {
            new MessageHandlerImpl().execute(msg);
        } catch (TelegramApiException e) {
            log.error("Не получилось отправить изменяемое сообщение " + e.getMessage());
        }
    }

    @Override
    public void executeEditMessageWithMarkup(String text, long chatId, long messageId) {
        EditMessageText msg = new EditMessageText();
        msg.setChatId(String.valueOf(chatId));
        msg.setText(text);
        msg.setReplyMarkup(buttons.getInlineKeyboardMarkup());
        msg.setMessageId((int) messageId);
        try {
            new MessageHandlerImpl().execute(msg);
        } catch (TelegramApiException e) {
            log.error("Не получилось отправить изменяемое сообщение " + e.getMessage());
        }
    }

    @Override
    public void executeMessage(SendMessage msg) {
        try {
            new MessageHandlerImpl().execute(msg);
        } catch (TelegramApiException e) {
            log.error(" Не удалось отправить сообщение " + e.getMessage());
        }
    }
}
