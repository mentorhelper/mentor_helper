package com.ua.javarush.mentor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailTemplates {
    CONFIRMATION("email_confirmation.ftl", "email.confirmation.subject"),
    RESET_PASSWORD("email_reset_password.ftl", "email.reset.password.subject");

    private final String emailTemplate;
    private final String subject;
}
