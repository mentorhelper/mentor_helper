package com.ua.javarush.mentor.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Role command")
public class RoleCommand {
    @Schema(description = "Role name")
    private String name;
}
