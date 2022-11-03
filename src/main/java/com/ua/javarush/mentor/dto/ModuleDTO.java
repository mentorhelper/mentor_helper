package com.ua.javarush.mentor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Module DTO")
public class ModuleDTO {
    @Schema(description = "Module id")
    private Long id;
    @Schema(description = "Module number")
    private int moduleNumber;
    @Schema(description = "Number of lectures")
    private int lectureCount;
    @Schema(description = "Module name")
    private String name;
    @Schema(description = "Module description")
    private String description;
    @Schema(description = "Module link")
    private String linkToSite;
}
