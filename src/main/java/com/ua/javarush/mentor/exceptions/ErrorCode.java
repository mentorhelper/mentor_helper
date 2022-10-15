package com.ua.javarush.mentor.exceptions;

import lombok.ToString;

@ToString
public class ErrorCode {

    private ErrorCode() {
    }

    public static final String APPLICATION_ERROR = "0";
    public static final String ROLE_ALREADY_EXISTS = "1";
    public static final String USER_NOT_FOUND = "2";
    public static final String ROLE_NOT_FOUND = "3";
    public static final String TELEGRAM_SEND_MESSAGE_ERROR = "4";
    public static final String TELEGRAM_ID_NOT_FOUND = "5";
    public static final String ROLE_PERMISSION_NOT_FOUND = "6";
    public static final String EMAIL_SEND_ERROR = "7";
    public static final String TOKEN_NOT_VALID = "8";
    public static final String TOKEN_EXPIRED = "9";
    public static final String UNABLE_TO_PARSE_JSON = "10";
    public static final String UNABLE_TO_CREATE_JSON = "11";
}
