package com.ua.javarush.mentor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User DTO")
public class UserDTO {
    @Schema(description = "User id")
    private Long id;
    @Schema(description = "First name")
    private String firstName;
    @Schema(description = "Last name")
    private String lastName;
    @Schema(description = "Country")
    private String country;
    @Schema(description = "Email")
    private String email;
    @Schema(description = "Username")
    private String username;
    @Schema(description = "Registered at")
    private String registeredAt;
    @Schema(description = "Telegram id")
    private Integer telegramId;
    @Schema(description = "Telegram nickname")
    private String telegramNickname;
    @Schema(description = "Salary per hour")
    private Integer salaryPerHour;
    @Schema(description = "Salary currency")
    private String salaryCurrency;
    @Schema(description = "Role name")
    private String roleName;
    @Schema(description = "Access token")
    private String accessToken;
    @Schema(description = "Session expired date")
    private Instant sessionExpiredDate;
}
