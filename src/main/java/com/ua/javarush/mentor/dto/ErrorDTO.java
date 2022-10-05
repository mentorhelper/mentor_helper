package com.ua.javarush.mentor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorDTO {
    private String code;
    private String message;
}
