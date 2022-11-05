package com.ua.javarush.mentor.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User command")
public class UserCommand {
    @Schema(description = "First name")
    private String firstName;
    @Schema(description = "Last name")
    private String lastName;
    @Schema(description = "Country")
    private String country;
    @Schema(description = "Telegram nickname")
    private String telegramNickname;
    @Schema(description = "Salary per hour")
    private Integer salaryPerHour;
    @Schema(description = "Salary currency")
    private String salaryCurrency;
    @Schema(description = "Role id")
    private Long roleId;
    @Schema(description = "Email")
    private String email;
    @Schema(description = "Password")
    private String password;
    @Schema(description = "Username")
    private String username;

}
