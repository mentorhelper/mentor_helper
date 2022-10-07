package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.command.RoleCommand;
import com.ua.javarush.mentor.dto.RoleDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.persist.model.Role;

import java.util.Map;

public interface RoleService {

    RoleDTO createRole(RoleCommand roleCommand);
    RoleDTO getRoleById(Long id) throws GeneralException;
    Map<String, Object> getAllRoles(int page, int size, String sortBy);
    RoleDTO getRolePermissionById(Long roleId) throws GeneralException;
    RoleDTO addPermissionToRole(Long roleId) throws GeneralException;
    RoleDTO removeRole(Long roleId) throws GeneralException;
    RoleDTO removePermissionInRole(Long roleId) throws GeneralException;

    Role fetchRole(Long roleId) throws GeneralException;
}
