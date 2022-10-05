package com.ua.javarush.mentor.exceptions;

import lombok.ToString;

@ToString
public class ErrorCode {

    private ErrorCode() {
    }

    public static final String ROLE_ALREADY_EXISTS = "1";
    public static final String USER_NOT_FOUND = "2";
    public static final String ROLE_NOT_FOUND = "3";
}
