package com.ua.javarush.mentor.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User message command")
public class UserMessageCommand {
    @Schema(description = "User id")
    private Long userId;
    @Schema(description = "Message id")
    private String message;
}
