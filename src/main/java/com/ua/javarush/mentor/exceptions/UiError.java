package com.ua.javarush.mentor.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum UiError {
    ROLE_ALREADY_EXISTS(UiErrorCode.ROLE_ALREADY_EXISTS, "Role already exists"),
    USER_NOT_FOUND(UiErrorCode.USER_NOT_FOUND, "Didn't found user"),
    ROLE_NOT_FOUND(UiErrorCode.ROLE_NOT_FOUND, "Didn't found role"),
    ROLE_PERMISSION_NOT_FOUND(UiErrorCode.ROLE_PERMISSION_NOT_FOUND, "Didn't found role permission"),
    TELEGRAM_SEND_MESSAGE_ERROR(UiErrorCode.TELEGRAM_SEND_MESSAGE_ERROR, "Telegram send message error"),
    TELEGRAM_ID_NOT_FOUND(UiErrorCode.TELEGRAM_ID_NOT_FOUND, "Telegram id not found"),
    PDF_EXPORT_ERROR(UiErrorCode.PDF_EXPORT_ERROR, "PDF export error"),
    EMAIL_SEND_ERROR(UiErrorCode.EMAIL_SEND_ERROR, "Email send error"),
    TOKEN_NOT_VALID(UiErrorCode.TOKEN_NOT_VALID, "Token not valid"),
    TOKEN_EXPIRED(UiErrorCode.TOKEN_EXPIRED, "Token expired"),
    CODE_NOT_VALID(UiErrorCode.CODE_NOT_VALID, "Code not valid"),
    CODE_EXPIRED(UiErrorCode.CODE_EXPIRED, "Code expired"),
    UNABLE_TO_PARSE_JSON(UiErrorCode.UNABLE_TO_PARSE_JSON, "Unable to parse json"),
    UNABLE_TO_CREATE_JSON(UiErrorCode.UNABLE_TO_CREATE_JSON, "Unable to create json"),
    PASSWORD_NOT_VALID(UiErrorCode.PASSWORD_NOT_VALID, "Password not valid"),
    EMAIL_NOT_VALID(UiErrorCode.EMAIL_NOT_VALID, "Email not valid"),
    USERNAME_NOT_VALID(UiErrorCode.USERNAME_NOT_VALID, "Username not valid"),
    COUNTRY_NOT_FOUND(UiErrorCode.COUNTRY_NOT_FOUND, "Country not found"),
    COUNTRY_NOT_SET(UiErrorCode.COUNTRY_NOT_SET, "Country not set"),
    USER_EMAIL_ALREADY_EXISTS(UiErrorCode.USER_EMAIL_ALREADY_EXISTS, "User email already exists"),
    USERNAME_ALREADY_EXISTS(UiErrorCode.USERNAME_ALREADY_EXISTS, "Username already exists"),
    MAX_COUNT_OF_RESET_PASSWORD_REACHED(UiErrorCode.MAX_COUNT_OF_RESET_PASSWORD_REACHED, "Max count of reset password reached"),
    OLD_PASSWORD_NOT_VALID(UiErrorCode.OLD_PASSWORD_NOT_VALID, "Old password not valid"),
    APPLICATION_ERROR(UiErrorCode.APPLICATION_ERROR, "Application error"),
    FILE_NOT_FOUND(UiErrorCode.FILE_NOT_FOUND, "File not found"),
    UNABLE_TO_GET_FILE_FROM_S3(UiErrorCode.UNABLE_TO_GET_FILE_FROM_S3, "Unable to get file from S3"),
    FILE_TYPE_NOT_DETERMINE(UiErrorCode.FILE_TYPE_NOT_DETERMINE, "File type not determine"),
    FILE_TYPE_NOT_SUPPORTED(UiErrorCode.FILE_TYPE_NOT_SUPPORTED, "File type not supported"),
    FILE_UPLOAD_ERROR(UiErrorCode.FILE_UPLOAD_ERROR, "File upload error");

    private final String code;
    private final String message;
}
