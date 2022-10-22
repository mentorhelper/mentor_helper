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
    public static final String PASSWORD_NOT_VALID = "12";
    public static final String EMAIL_NOT_VALID = "13";
    public static final String USERNAME_NOT_VALID = "14";
    public static final String COUNTRY_NOT_FOUND = "15";
    public static final String COUNTRY_NOT_SET = "16";
    public static final String USER_EMAIL_ALREADY_EXISTS = "17";
    public static final String USERNAME_ALREADY_EXISTS = "18";
    public static final String MAX_COUNT_OF_RESET_PASSWORD_REACHED = "19";
    public static final String OLD_PASSWORD_NOT_VALID = "20";
    public static final String CODE_NOT_VALID = "21";
    public static final String CODE_EXPIRED = "22";
}
