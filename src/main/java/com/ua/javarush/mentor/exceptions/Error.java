package com.ua.javarush.mentor.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum Error {
    ROLE_ALREADY_EXISTS(ErrorCode.ROLE_ALREADY_EXISTS, "Role already exists"),
    USER_NOT_FOUND(ErrorCode.USER_NOT_FOUND, "Didn't found user"),
    ROLE_NOT_FOUND(ErrorCode.ROLE_NOT_FOUND, "Didn't found role"),
    ROLE_PERMISSION_NOT_FOUND(ErrorCode.ROLE_PERMISSION_NOT_FOUND, "Didn't found role permission"),
    TELEGRAM_SEND_MESSAGE_ERROR(ErrorCode.TELEGRAM_SEND_MESSAGE_ERROR, "Telegram send message error"),
    TELEGRAM_ID_NOT_FOUND(ErrorCode.TELEGRAM_ID_NOT_FOUND, "Telegram id not found"),
    EMAIL_SEND_ERROR(ErrorCode.EMAIL_SEND_ERROR, "Email send error"),
    TOKEN_NOT_VALID(ErrorCode.TOKEN_NOT_VALID, "Token not valid"),
    TOKEN_EXPIRED(ErrorCode.TOKEN_EXPIRED, "Token expired"),
    APPLICATION_ERROR(ErrorCode.APPLICATION_ERROR, "Application error");

    private final String code;
    private final String message;
}
