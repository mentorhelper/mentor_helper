package com.ua.javarush.mentor.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Module command")
public class ModuleCommand {
    @Schema(description = "Module number")
    private Long moduleNumber;
    @Schema(description = "Number of lectures")
    private int lectureCount;
    @Schema(description = "Module name")
    private String name;
    @Schema(description = "Module description")
    private String description;
    @Schema(description = "Module link")
    private String linkToSite;

}
