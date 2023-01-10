package com.ua.javarush.mentor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "File DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDTO {
    private String name;
    private String url;
}
