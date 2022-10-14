package com.ua.javarush.mentor.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum UiError {
    ROLE_ALREADY_EXISTS(ErrorCode.ROLE_ALREADY_EXISTS, "Role already exists"),
    USER_NOT_FOUND(ErrorCode.USER_NOT_FOUND, "Didn't found user"),
    ROLE_NOT_FOUND(ErrorCode.ROLE_NOT_FOUND, "Didn't found role"),
    ROLE_PERMISSION_NOT_FOUND(ErrorCode.ROLE_PERMISSION_NOT_FOUND, "Didn't found role permission"),
    TELEGRAM_SEND_MESSAGE_ERROR(ErrorCode.TELEGRAM_SEND_MESSAGE_ERROR, "Telegram send message error"),
    TELEGRAM_ID_NOT_FOUND(ErrorCode.TELEGRAM_ID_NOT_FOUND, "Telegram id not found"),
    PDF_EXPORT_ERROR(ErrorCode.PDF_EXPORT_ERROR, "PDF export error"),
    APPLICATION_ERROR(ErrorCode.APPLICATION_ERROR, "Application error");

    private final String code;
    private final String message;
}
