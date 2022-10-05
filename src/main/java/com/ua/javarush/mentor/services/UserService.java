package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.controller.user.UserRequest;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;

import java.util.List;

public interface UserService {
    UserDTO create(UserRequest userRequest);
    List<UserDTO> getAllUsers();
    List<UserDTO> getAllUsers(Integer page);
    UserDTO getUserById(Long id) throws GeneralException;
    UserDTO removeUser(Long id) throws GeneralException;
    UserDTO changePermission(Long id, Long roleId) throws GeneralException;
}
