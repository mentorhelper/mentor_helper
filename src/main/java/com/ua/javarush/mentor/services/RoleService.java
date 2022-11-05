package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.command.RoleCommand;
import com.ua.javarush.mentor.command.RoleToPermissionCommand;
import com.ua.javarush.mentor.dto.PageDTO;
import com.ua.javarush.mentor.dto.RoleDTO;
import com.ua.javarush.mentor.dto.RoleToPermissionDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.persist.model.Role;

public interface RoleService {

    RoleDTO createRole(RoleCommand roleCommand);

    RoleDTO getRoleById(Long id) throws GeneralException;

    PageDTO<RoleDTO> getAllRoles(int page, int size, String sortBy);

    RoleToPermissionDTO getRolePermissionById(Long roleId) throws GeneralException;

    RoleToPermissionDTO addPermissionToRole(Long roleId, RoleToPermissionCommand roleToPermissionCommand) throws GeneralException;

    void removeRole(Long roleId) throws GeneralException;

    void removePermissionInRole(Long roleId, RoleToPermissionCommand roleToPermissionCommand) throws GeneralException;

    Role fetchRole(Long roleId) throws GeneralException;

    Role findByRole(String role) throws GeneralException;
}
