package com.ua.javarush.mentor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Role to permission DTO")
public class RoleToPermissionDTO {
    @Schema(description = "Role permission")
    private String permission;
}
