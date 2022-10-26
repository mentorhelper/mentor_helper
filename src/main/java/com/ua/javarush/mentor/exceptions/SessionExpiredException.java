package com.ua.javarush.mentor.exceptions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SessionExpiredException extends RuntimeException {
    private final String refreshToken;

    public SessionExpiredException(String refreshToken) {
        super(String.format("Refresh Token %s has expired", refreshToken));
        this.refreshToken = refreshToken;
    }
}
