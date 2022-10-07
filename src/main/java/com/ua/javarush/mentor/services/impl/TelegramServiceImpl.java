package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.bot.MentorHelperBot;
import com.ua.javarush.mentor.exceptions.Error;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.services.TelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.ua.javarush.mentor.exceptions.GeneralExceptionUtils.createGeneralException;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Service
public class TelegramServiceImpl implements TelegramService {

    private final MentorHelperBot mentorHelperBot;

    public TelegramServiceImpl(MentorHelperBot mentorHelperBot) {
        this.mentorHelperBot = mentorHelperBot;
    }

    @Override
    public void sendMessage(Long userId, String message) throws GeneralException {
        try {
            mentorHelperBot.execute(buildMessage(userId, message));
        } catch (Exception e) {
            log.error("Error while sending message to user with id {}", userId, e);
            throw createGeneralException("Cannot send message to user with id " + userId, BAD_REQUEST, Error.TELEGRAM_SEND_MESSAGE_ERROR, e);
        }

    }

    private SendMessage buildMessage(Long userId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userId);
        sendMessage.setText(message);
        return sendMessage;
    }
}
