package com.ua.javarush.mentor.command;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginCommand {
    @NotEmpty
    private String email;

    @NotBlank
    @Size(min = 8, max = 24)
    private String password;
}
