package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.command.NotificationCommand;
import com.ua.javarush.mentor.command.SendEmailCommand;
import com.ua.javarush.mentor.enums.NotificationProvider;
import com.ua.javarush.mentor.exceptions.GeneralException;

public interface NotificationService {
    void saveNotification(NotificationCommand notificationCommand);

    NotificationCommand createNotification(SendEmailCommand sendEmailCommand, NotificationProvider provider) throws GeneralException;
}
