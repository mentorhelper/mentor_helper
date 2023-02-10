package com.ua.javarush.mentor.config;

import com.ua.javarush.mentor.bot.MentorHelperBot;
import com.ua.javarush.mentor.bot.BotFacade;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Getter
@Configuration
public class BotConfig {

    @Value("${telegramBot.username}")
    private String botToken;

    @Value("${telegramBot.token}")
    private String botUsername;

    @Value("${telegramBot.path}")
    private String botPath;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(getBotPath()).build();
    }

    @Bean
    public MentorHelperBot mentorHelperBot(SetWebhook setWebhookInstance, BotFacade botFacade) {
        MentorHelperBot mentorHelperBot = new MentorHelperBot(setWebhookInstance, botFacade);
        mentorHelperBot.setBotToken(getBotToken());
        mentorHelperBot.setBotUsername(getBotUsername());
        mentorHelperBot.setBotPath(getBotPath());

        return mentorHelperBot;
    }
}
