package com.ua.javarush.mentor.command;

import lombok.Data;

@Data
public class UserCommand {
    private String firstName;
    private String lastName;
    private String country;
    private String telegramNickname;
    private Integer salaryPerHour;
    private String salaryCurrency;
    private String secretPhrase;
    private Long roleId;
}
