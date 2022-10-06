package com.ua.javarush.mentor.command;

import lombok.Data;

@Data
public class UserPermissionCommand {
    private Long userId;
    private Long roleId;
}
