package com.ua.javarush.mentor.bot;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Slf4j
@Getter
public class MentorHelperBot extends SpringWebhookBot {
    @Setter
    private String botUsername;
    @Setter
    private String botToken;
    @Setter
    private String botPath;

    private final BotFacade botFacade;
    private MessageSource messageSource;

    public MentorHelperBot(SetWebhook setWebhook, BotFacade botFacade) {
        super(setWebhook);
        this.botFacade = botFacade;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return botFacade.handleUpdate(update);
    }
}