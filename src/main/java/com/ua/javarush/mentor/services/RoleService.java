package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.command.RoleCommand;
import com.ua.javarush.mentor.dto.RoleDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.persist.model.Role;

public interface RoleService {

    RoleDTO createRole(RoleCommand roleCommand);
    RoleDTO getRoleById(Long id) throws GeneralException;
    Role fetchRole(Long roleId) throws GeneralException;
}
