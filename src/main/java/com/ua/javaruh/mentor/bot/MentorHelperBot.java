package com.ua.javaruh.mentor.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MentorHelperBot extends TelegramLongPollingBot {
    private static final String GREETINGS = "Welcome to MentorHelper";

    @Override
    public String getBotToken() {
        return System.getProperty("MENTOR_BOT_USERNAME");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId());

            String answer = switch (update.getMessage().getText()) {
                case "/start" -> GREETINGS;
                default -> "type '/start' for beginning";
            };
            message.setText(answer);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return System.getProperty("MENTOR_BOT_USERNAME");
    }
}
