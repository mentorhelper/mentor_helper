package com.ua.javarush.mentor.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class GeneralException extends Exception {

    private final String message;
    private final HttpStatus httpStatus;
    private final List<UiError> uiErrors;

    public GeneralException(String message, HttpStatus status, List<UiError> uiErrors) {
        super(message);
        this.message = message;
        this.httpStatus = status;
        this.uiErrors = uiErrors;
    }
}

