package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.command.UserCommand;
import com.ua.javarush.mentor.command.UserMessageCommand;
import com.ua.javarush.mentor.command.UserPermissionCommand;
import com.ua.javarush.mentor.dto.PageDTO;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.persist.model.User;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserService {

    UserDetails loadUserDetailsByUserId(Long id) throws GeneralException;

    UserDetails loadUserByUsername(String username) throws GeneralException;

    void matchPassword(User user, String password) throws GeneralException;

    UserDTO findUserById(Long id) throws GeneralException;

    User findUserByEmail(String email) throws GeneralException;

    UserDTO createUser(UserCommand userCommand) throws GeneralException;

    PageDTO<UserDTO> getAllUsers(int page, int size, String sortBy);

    UserDTO getUserById(Long id) throws GeneralException;

    void removeUser(Long id) throws GeneralException;

    void changePermission(UserPermissionCommand userPermissionCommand) throws GeneralException;

    void sendMessage(UserMessageCommand userMessageCommand) throws GeneralException;

    void sendConfirmationEmail(String email) throws GeneralException;

    void confirmEmail(String token, String email) throws GeneralException;
}
