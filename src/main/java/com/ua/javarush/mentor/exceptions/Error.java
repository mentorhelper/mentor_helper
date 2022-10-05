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
    ROLE_NOT_FOUND(ErrorCode.ROLE_NOT_FOUND, "Didn't found role");

    private final String code;
    private final String message;
}
