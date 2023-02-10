package com.ua.javarush.mentor.bot;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class Keyboard {

    private final String MENTOR_BUTTON_NAME = "Ментор";
    private final String ADMIN_BUTTON_NAME = "Администратор";

    private final ReplyKeyboardMarkup keyboardMarkup;

    public Keyboard() {
        this.keyboardMarkup = new ReplyKeyboardMarkup();
        this.keyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow rowMentor = new KeyboardRow();
        KeyboardRow rowAdmin = new KeyboardRow();

        rowMentor.add(new KeyboardButton(MENTOR_BUTTON_NAME));
        rowAdmin.add(new KeyboardButton(ADMIN_BUTTON_NAME));

        keyboardRows.add(rowMentor);
        keyboardRows.add(rowAdmin);

        this.keyboardMarkup.setKeyboard(keyboardRows);
    }
}
