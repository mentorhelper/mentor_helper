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
    MODULE_NOT_FOUND(ErrorCode.MODEL_NOT_FOUND, "Module not found"),
    ROLE_PERMISSION_NOT_FOUND(ErrorCode.ROLE_PERMISSION_NOT_FOUND, "Didn't found role permission"),
    TELEGRAM_SEND_MESSAGE_ERROR(ErrorCode.TELEGRAM_SEND_MESSAGE_ERROR, "Telegram send message error"),
    TELEGRAM_ID_NOT_FOUND(ErrorCode.TELEGRAM_ID_NOT_FOUND, "Telegram id not found"),
    EMAIL_SEND_ERROR(ErrorCode.EMAIL_SEND_ERROR, "Email send error"),
    TOKEN_NOT_VALID(ErrorCode.TOKEN_NOT_VALID, "Token not valid"),
    TOKEN_EXPIRED(ErrorCode.TOKEN_EXPIRED, "Token expired"),
    CODE_NOT_VALID(ErrorCode.CODE_NOT_VALID, "Code not valid"),
    CODE_EXPIRED(ErrorCode.CODE_EXPIRED, "Code expired"),
    UNABLE_TO_PARSE_JSON(ErrorCode.UNABLE_TO_PARSE_JSON, "Unable to parse json"),
    UNABLE_TO_CREATE_JSON(ErrorCode.UNABLE_TO_CREATE_JSON, "Unable to create json"),
    PASSWORD_NOT_VALID(ErrorCode.PASSWORD_NOT_VALID, "Password not valid"),
    EMAIL_NOT_VALID(ErrorCode.EMAIL_NOT_VALID, "Email not valid"),
    USERNAME_NOT_VALID(ErrorCode.USERNAME_NOT_VALID, "Username not valid"),
    COUNTRY_NOT_FOUND(ErrorCode.COUNTRY_NOT_FOUND, "Country not found"),
    COUNTRY_NOT_SET(ErrorCode.COUNTRY_NOT_SET, "Country not set"),
    USER_EMAIL_ALREADY_EXISTS(ErrorCode.USER_EMAIL_ALREADY_EXISTS, "User email already exists"),
    USERNAME_ALREADY_EXISTS(ErrorCode.USERNAME_ALREADY_EXISTS, "Username already exists"),
    MAX_COUNT_OF_RESET_PASSWORD_REACHED(ErrorCode.MAX_COUNT_OF_RESET_PASSWORD_REACHED, "Max count of reset password reached"),
    OLD_PASSWORD_NOT_VALID(ErrorCode.OLD_PASSWORD_NOT_VALID, "Old password not valid"),
    APPLICATION_ERROR(ErrorCode.APPLICATION_ERROR, "Application error");

    private final String code;
    private final String message;
}
