package com.ua.javarush.mentor.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Command for changing password")
public class ChangePasswordCommand {
    @Schema(description = "old password", example = "1")
    private String oldPassword;
    @Schema(description = "new password", example = "1")
    private String newPassword;
}
