package com.ua.javarush.mentor.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Configuration
public class MentorHelperBot extends TelegramLongPollingBot {
    private static final String GREETINGS = "Welcome to MentorHelper";

    private String botUsername;
    private String botToken;

    @Bean
    public MentorHelperBot uploadBot(@Value("${telegramBot.username}") String botUsername,
                                     @Value("${telegramBot.token}") String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
            log.info("Bot {} was registered", botUsername);
        } catch (TelegramApiException e) {
            log.error("Error while registering bot", e);
        }
        return this;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

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
    public String getBotUsername() {
        return botUsername;
    }
}
