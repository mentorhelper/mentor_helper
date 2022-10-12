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
@Schema(description = "Role DTO")
public class RoleDTO {
    @Schema(description = "Role id")
    private Long id;
    @Schema(description = "Role name")
    private String name;
}
