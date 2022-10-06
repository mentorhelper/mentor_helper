package com.ua.javarush.mentor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String country;
    private Date registeredAt;
    private Integer telegramId;
    private String telegramNickname;
    private Integer salaryPerHour;
    private String salaryCurrency;
    private String secretPhrase;
    private String roleName;
}
