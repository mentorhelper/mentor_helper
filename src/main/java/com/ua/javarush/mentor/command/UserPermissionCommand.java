package com.ua.javarush.mentor.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User permission command")
public class UserPermissionCommand {
    @Schema(description = "User id")
    private Long userId;
    @Schema(description = "Role id")
    private Long roleId;
}
