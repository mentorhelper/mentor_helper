package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.command.UserCommand;
import com.ua.javarush.mentor.command.UserMessageCommand;
import com.ua.javarush.mentor.command.UserPermissionCommand;
import com.ua.javarush.mentor.dto.PageDTO;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;


public interface UserService {
    UserDTO createUser(UserCommand userCommand) throws GeneralException;

    PageDTO<UserDTO> getAllUsers(int page, int size, String sortBy);

    UserDTO getUserById(Long id) throws GeneralException;

    void removeUser(Long id) throws GeneralException;

    void changePermission(UserPermissionCommand userPermissionCommand) throws GeneralException;

    void sendMessage(UserMessageCommand userMessageCommand) throws GeneralException;
}
