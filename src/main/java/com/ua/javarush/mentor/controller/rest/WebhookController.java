package com.ua.javarush.mentor.controller.rest;

import com.ua.javarush.mentor.bot.MentorHelperBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebhookController {

    private final MentorHelperBot mentorHelperBot;

    public WebhookController(MentorHelperBot mentorHelperBot) {
        this.mentorHelperBot = mentorHelperBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return mentorHelperBot.onWebhookUpdateReceived(update);
    }

}
