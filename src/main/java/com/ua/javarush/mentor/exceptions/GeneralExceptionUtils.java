package com.ua.javarush.mentor.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public class GeneralExceptionUtils {

    private GeneralExceptionUtils() {
    }

    public static GeneralException createGeneralException(String code, HttpStatus status, List<Error> errors) {
        return new GeneralException(code, status, errors);
    }

    public static GeneralException createGeneralException(String code, HttpStatus status, Error error) {
        return new GeneralException(code, status, buildErrorList(error));
    }

    public static List<Error> buildErrorList(Error error) {
        return List.of(error);
    }
}
