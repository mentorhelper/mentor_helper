package com.ua.javarush.mentor.mapper;

import com.ua.javarush.mentor.command.RoleToPermissionCommand;
import com.ua.javarush.mentor.dto.RoleToPermissionDTO;
import com.ua.javarush.mentor.persist.model.Role;
import com.ua.javarush.mentor.persist.model.RoleToPermission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface RoleToPermissionMapper {
    @Mapping(target = "permissions", expression = "java(role.getPermissions().stream().map(RoleToPermission::getPermission).toList())")
    RoleToPermissionDTO mapToDto(Role role);

    @Mapping(target = "roleId", source = "roleId")
    @Mapping(target = "permission", expression = "java(roleToPermissionCommand.getPermission())")
    RoleToPermission mapToEntity(Long roleId, RoleToPermissionCommand roleToPermissionCommand);
}
