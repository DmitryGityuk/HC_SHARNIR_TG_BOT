package com.example.HC_SHARNIR_BOT.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class Buttons {
    private final String YES = "YES_BUTTON";
    private final String NO = "NO_BUTTON";

    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var yesButton = new InlineKeyboardButton();
        yesButton.setText("+");
        yesButton.setCallbackData(YES);
        var noButton = new InlineKeyboardButton();
        noButton.setText("-");
        noButton.setCallbackData(NO);

        rowInLine.add(yesButton);
        rowInLine.add(noButton);
        rowsInLine.add(rowInLine);
        inlineMarkup.setKeyboard(rowsInLine);
        return inlineMarkup;
    }
}
