package com.ua.javarush.mentor.exceptions;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class GeneralExceptionUtils {

    private GeneralExceptionUtils() {
    }

    public static GeneralException createGeneralException(String message, HttpStatus status, List<UiError> uiErrors, Exception exception) {
        List<UiError> uiErrorList = new ArrayList<>(uiErrors);
        if (exception instanceof GeneralException) {
            uiErrorList.addAll(((GeneralException) exception).getUiErrors());
        }
        return new GeneralException(message, status, uiErrorList);
    }

    public static GeneralException createGeneralException(String message, HttpStatus status, List<UiError> uiErrors) {
        return new GeneralException(message, status, uiErrors);
    }

    public static GeneralException createGeneralException(String message, HttpStatus status, UiError uiError, Exception exception) {
        List<UiError> uiErrors = new ArrayList<>(List.of(uiError));
        if (exception instanceof GeneralException) {
            uiErrors.addAll(((GeneralException) exception).getUiErrors());
        }
        return new GeneralException(message, status, uiErrors);
    }

    public static GeneralException createGeneralException(String message, HttpStatus status, UiError uiError) {
        return new GeneralException(message, status, buildErrorList(uiError));
    }

    public static List<UiError> buildErrorList(UiError uiError) {
        return List.of(uiError);
    }
}
