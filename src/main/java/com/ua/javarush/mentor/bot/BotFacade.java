package com.ua.javarush.mentor.bot;

import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.services.TelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class BotFacade {

    private final TelegramService telegramService;

    public BotFacade(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    public SendMessage handleUpdate(Update update) {
        SendMessage replyMessage = null;

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("Message '{}' from user with id {}",
                    update.getMessage().getText(), update.getMessage().getFrom().getId());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        long chatId = message.getChatId();
        String textRequest = message.getText().toLowerCase();

        String messageToSend = "Команда не найдена";

        // TODO Нужны состояния бота
        // TODO Хранение состояний бота
        // TODO Обработчики состояний
        // TODO Возможные команды для бота в enum
        try {
            if ("/start".equals(textRequest)) {
                messageToSend = "Привет! Выберите вашу роль...";
            } else if ("ментор".equals(textRequest)) {
                messageToSend = "Отправьте email указанный при регистрации";
            } else if ("администратор".equals(textRequest)) {
                messageToSend = "Отправьте email указанный при регистрации";
            }

            return telegramService.sendMessage(chatId, messageToSend);

        } catch (GeneralException e) {
            //TODO  залоггировать и отправить сообщение о проблеме в чат
        }

        return null;
    }
}
