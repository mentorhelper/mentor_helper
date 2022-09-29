package com.ua.javaruh.mentor.bot;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MentorHelperBot extends TelegramLongPollingBot {
    private static final String GREETINGS = "Welcome to MentorHelper";
    @Value("${telegramBot.token}")
    private String token;

    @Value("${telegramBot.username}")
    private String username;

    @Override
    public String getBotToken() {
        return token;
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
        return username;
    }
}
