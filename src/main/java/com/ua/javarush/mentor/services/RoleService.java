package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.command.RoleCommand;
import com.ua.javarush.mentor.command.RoleToPermissionCommand;
import com.ua.javarush.mentor.dto.PageDTO;
import com.ua.javarush.mentor.dto.RoleDTO;
import com.ua.javarush.mentor.dto.RoleToPermissionDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.persist.model.Role;
import com.ua.javarush.mentor.persist.model.RoleToPermission;

public interface RoleService {

    RoleDTO createRole(RoleCommand roleCommand);

    RoleDTO getRoleById(Long id) throws GeneralException;

    PageDTO<RoleDTO> getAllRoles(int page, int size, String sortBy);

    RoleToPermissionDTO getRolePermissionById(Long roleId) throws GeneralException;

    RoleToPermissionDTO addPermissionToRole(Long roleId, RoleToPermissionCommand roleToPermissionCommand);

    void removeRole(Long roleId) throws GeneralException;

    void removePermissionInRole(Long roleId, RoleToPermissionCommand roleToPermissionCommand);

    Role fetchRole(Long roleId) throws GeneralException;

    RoleToPermission fetchRoleToPermission(Long roleId) throws GeneralException;
}
