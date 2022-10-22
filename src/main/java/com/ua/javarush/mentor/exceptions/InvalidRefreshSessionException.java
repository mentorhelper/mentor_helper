package com.ua.javarush.mentor.exceptions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InvalidRefreshSessionException extends RuntimeException {
    private final String fingerPrint;

    public InvalidRefreshSessionException(String fingerPrint) {
        super(String.format("Invalid refresh session by finger print '%s'", fingerPrint));
        this.fingerPrint = fingerPrint;
    }
}
