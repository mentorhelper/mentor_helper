package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.command.SendEmailCommand;
import com.ua.javarush.mentor.enums.AppLocale;
import com.ua.javarush.mentor.enums.EmailTemplates;
import com.ua.javarush.mentor.exceptions.GeneralException;

import java.util.Map;

public interface EmailService {
    void sendConfirmationEmail(SendEmailCommand sendEmailCommand) throws GeneralException;

    void sendResetPasswordEmail(SendEmailCommand sendEmailCommand) throws GeneralException;

    SendEmailCommand buildEmail(String email, AppLocale appLocale, EmailTemplates emailTemplates, Map<String, String> newParams);
}

