package com.ua.javarush.mentor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailTemplates {
    CONFIRMATION("email_confirmation.ftl", "email.confirmation.subject");

    private final String emailTemplate;
    private final String subject;
}
