package com.ua.javarush.mentor.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class MentorHelperBot extends TelegramLongPollingBot {
    private static final String GREETINGS = "Welcome to MentorHelper";

    @Value("${telegramBot.username}")
    private String botUsername;
    @Value("${telegramBot.token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info("Message '{}' from user with id {}", update.getMessage().getText(), update.getMessage().getFrom().getId());
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId());

            String messageText = update.getMessage().getText() != null ? update.getMessage().getText() : "";
            switch (messageText) {
                case "/start":
                    message.setText(GREETINGS);
                    break;
                default:
                    message.setText("I don't understand you");
            }
            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
