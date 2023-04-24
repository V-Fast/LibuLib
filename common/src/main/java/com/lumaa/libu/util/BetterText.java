package com.lumaa.libu.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;

public class BetterText {
    private Component text;

    public BetterText(String string, TextType type) {
        if (type.isTranslation) {
            this.text = Component.translatable(string);
        } else {
            this.text = Component.literal(string);
        }
    }

    public BetterText(String string) {
        this.text = Component.translatable(string);
    }

    public Component withColor(int rgbColor) {
        return this.text.copy().setStyle(this.text.getStyle().withColor(rgbColor));
    }

    public Component withColor(TextColor color) {
        return this.text.copy().setStyle(this.text.getStyle().withColor(color));
    }

    public Component withColor(ChatFormatting color) {
        return this.text.copy().setStyle(this.text.getStyle().withColor(color));
    }

    public enum TextType {
        LITERAL(false),
        TRANSLATION(true);

        public boolean isTranslation;

        TextType(boolean isTranslation) {
            this.isTranslation = isTranslation;
        }
    }
}
