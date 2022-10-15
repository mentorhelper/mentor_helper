package com.ua.javarush.mentor.command;

import com.ua.javarush.mentor.enums.NotificationProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Notification command")
public class NotificationCommand {
    @Schema(description = "Notification data")
    private String data;
    @Schema(description = "Notification provider")
    private NotificationProvider provider;
}
