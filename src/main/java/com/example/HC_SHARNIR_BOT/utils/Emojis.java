package com.example.HC_SHARNIR_BOT.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emojis {
    POINT_DOWN(EmojiParser.parseToUnicode(":point_down:")),
    MOYAI(EmojiParser.parseToUnicode(":moyai:")),
    CALLING(EmojiParser.parseToUnicode(":calling:")),
    CHART_WITH_UPWARDS_TREND(EmojiParser.parseToUnicode(":chart_with_upwards_trend:")),
    MUSCLE(EmojiParser.parseToUnicode(":muscle:"));

    private String emojiName;

    @Override
    public String toString() { return emojiName; }
}
