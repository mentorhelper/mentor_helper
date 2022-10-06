package com.ua.javarush.mentor.exceptions;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class GeneralExceptionUtils {

    private GeneralExceptionUtils() {
    }

    public static GeneralException createGeneralException(String message, HttpStatus status, List<Error> errors, Exception exception) {
        List<Error> errorList = new ArrayList<>(errors);
        if (exception instanceof GeneralException) {
            errorList.addAll(((GeneralException) exception).getErrors());
        }
        return new GeneralException(message, status, errorList);
    }

    public static GeneralException createGeneralException(String message, HttpStatus status, List<Error> errors) {
        return new GeneralException(message, status, errors);
    }

    public static GeneralException createGeneralException(String message, HttpStatus status, Error error, Exception exception) {
        List<Error> errors = new ArrayList<>(List.of(error));
        if (exception instanceof GeneralException) {
            errors.addAll(((GeneralException) exception).getErrors());
        }
        return new GeneralException(message, status, errors);
    }

    public static GeneralException createGeneralException(String message, HttpStatus status, Error error) {
        return new GeneralException(message, status, buildErrorList(error));
    }

    public static List<Error> buildErrorList(Error error) {
        return List.of(error);
    }
}
