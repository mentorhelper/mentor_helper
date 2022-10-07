package com.ua.javarush.mentor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Error DTO")
public class ErrorDTO {
    @Schema(description = "Error code")
    private String code;
    @Schema(description = "Error message")
    private String message;
}
