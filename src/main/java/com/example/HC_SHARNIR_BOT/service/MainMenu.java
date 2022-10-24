package com.example.HC_SHARNIR_BOT.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainMenu {

    public List<BotCommand> getCommandList() {
        List<BotCommand> commandList = new ArrayList<>();
        commandList.add(new BotCommand("/start", "get a welcome message"));
        commandList.add(new BotCommand("/mydata", "get info about you"));
        commandList.add(new BotCommand("/topscorers", "get info top 3 scorers"));
        commandList.add(new BotCommand("/topassists", "get info top 3 assists"));
        commandList.add(new BotCommand("/topcomb", "get info top 3 players (goals + assists)"));
        commandList.add(new BotCommand("/help", "info how to used this bot"));
        return commandList;
    }

    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        row1.add(new KeyboardButton("Регистрация"));
        row2.add(new KeyboardButton("Про меня"));
        row2.add(new KeyboardButton("Помощь"));
        row3.add(new KeyboardButton("Голы"));
        row3.add(new KeyboardButton("Голевые пасы"));
        row3.add(new KeyboardButton("Голы + Пасы"));
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}
