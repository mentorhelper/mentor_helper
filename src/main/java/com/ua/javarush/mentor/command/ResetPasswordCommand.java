package com.ua.javarush.mentor.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Command for resetting password")
public class ResetPasswordCommand {
    @Schema(description = "email", example = "1")
    private String email;
    @Schema(description = "code ", example = "1")
    private String code;
    @Schema(description = "new password", example = "1")
    private String newPassword;
}
