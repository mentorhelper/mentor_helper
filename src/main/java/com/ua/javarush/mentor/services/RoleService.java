package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.command.RoleCommand;
import com.ua.javarush.mentor.dto.RoleDTO;

public interface RoleService {

    RoleDTO create(RoleCommand roleCommand);
}
