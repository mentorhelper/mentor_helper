package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.bot.Keyboard;
import com.ua.javarush.mentor.exceptions.UiError;
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

    private final Keyboard keyboard;

    public TelegramServiceImpl(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage sendMessage(Long userId, String message) throws GeneralException {
        try {
            return buildMessage(userId, message);
        } catch (Exception e) {
            log.error("Error while sending message to user with id {}", userId, e);
            throw createGeneralException("Cannot send message to user with id " + userId, BAD_REQUEST, UiError.TELEGRAM_SEND_MESSAGE_ERROR, e);
        }

    }

    private SendMessage buildMessage(Long userId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userId);
        sendMessage.setText(message);

        sendMessage.setReplyMarkup(keyboard.getKeyboardMarkup());
        sendMessage.enableHtml(true);
        return sendMessage;
    }
}
