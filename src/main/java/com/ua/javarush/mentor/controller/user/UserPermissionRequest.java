package com.ua.javarush.mentor.controller.user;

import lombok.Data;

@Data
public class UserPermissionRequest {
    private Long userId;
    private Long roleId;
}
