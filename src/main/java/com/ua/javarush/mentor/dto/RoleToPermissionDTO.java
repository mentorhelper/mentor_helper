package com.ua.javarush.mentor.dto;

import com.ua.javarush.mentor.persist.model.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Role to permission DTO")
public class RoleToPermissionDTO {
    @Schema(description = "Role permission")
    private List<PermissionType> permissions;
}
