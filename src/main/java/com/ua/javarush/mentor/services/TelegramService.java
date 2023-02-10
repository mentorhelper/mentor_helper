package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.exceptions.GeneralException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TelegramService {
    SendMessage sendMessage(Long userId, String message) throws GeneralException;
}
