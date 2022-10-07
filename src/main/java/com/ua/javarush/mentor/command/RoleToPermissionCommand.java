package com.ua.javarush.mentor.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Role permission command")
public class RoleToPermissionCommand {
    @Schema(description = "Role permission")
    private String permission;
}
