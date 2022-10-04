package com.ua.javarush.mentor.controller;

import com.ua.javarush.mentor.dto.ErrorDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.mapper.ErrorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    private final ErrorMapper errorMapper;

    public ExceptionHandlerController(ErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<List<ErrorDTO>> handleGeneralException(GeneralException e) {
        log.error("GeneralException with message: '{}'", e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(errorMapper.mapToDto(e.getErrors()));
    }
}