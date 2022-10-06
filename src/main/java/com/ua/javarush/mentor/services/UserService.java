package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.command.UserCommand;
import com.ua.javarush.mentor.command.UserPermissionCommand;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserCommand userCommand) throws GeneralException;
    List<UserDTO> getAllUsers(Integer page);
    UserDTO getUserById(Long id) throws GeneralException;
    void removeUser(Long id) throws GeneralException;
    void changePermission(UserPermissionCommand userPermissionCommand) throws GeneralException;
}
