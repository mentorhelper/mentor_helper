package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.command.*;
import com.ua.javarush.mentor.dto.FileDTO;
import com.ua.javarush.mentor.dto.PageDTO;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.enums.AppLocale;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.persist.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;


public interface UserService {

    UserDetails loadUserDetailsByUserId(Long id) throws GeneralException;

    void matchPassword(User user, String password) throws GeneralException;

    User findUserByEmail(String email) throws GeneralException;

    User findUserByUsername(String username) throws GeneralException;

    UserDTO createUser(UserCommand userCommand) throws GeneralException;

    PageDTO<UserDTO> getAllUsers(int page, int size, String sortBy);

    UserDTO getUserById(Long id) throws GeneralException;

    void removeUser(Long id) throws GeneralException;

    void changePermission(UserPermissionCommand userPermissionCommand) throws GeneralException;

    void sendMessage(UserMessageCommand userMessageCommand) throws GeneralException;

    void sendConfirmationEmail(String email) throws GeneralException;

    void confirmEmail(String token, String email) throws GeneralException;

    void resetPassword(String email) throws GeneralException;

    void changePassword(ChangePasswordCommand changePasswordCommand, Principal principal) throws GeneralException;

    void confirmResetPassword(ResetPasswordCommand resetPasswordCommand) throws GeneralException;

    void exportToPDF(HttpServletResponse response, AppLocale appLocale) throws GeneralException;

    boolean isEmailExists(String email);

    FileDTO uploadFile(FileCommand fileCommand, Principal principal) throws GeneralException;
}
