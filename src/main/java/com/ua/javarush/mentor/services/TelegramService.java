package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.exceptions.GeneralException;

public interface TelegramService {
    void sendMessage(Long userId, String message) throws GeneralException;
}
