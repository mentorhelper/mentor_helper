package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.command.SendEmailCommand;
import com.ua.javarush.mentor.command.UserCommand;
import com.ua.javarush.mentor.command.UserMessageCommand;
import com.ua.javarush.mentor.command.UserPermissionCommand;
import com.ua.javarush.mentor.dto.PageDTO;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.enums.AppLocale;
import com.ua.javarush.mentor.enums.EmailTemplates;
import com.ua.javarush.mentor.exceptions.Error;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.mapper.UserMapper;
import com.ua.javarush.mentor.persist.model.Role;
import com.ua.javarush.mentor.persist.model.User;
import com.ua.javarush.mentor.persist.repository.UserRepository;
import com.ua.javarush.mentor.services.EmailService;
import com.ua.javarush.mentor.services.RoleService;
import com.ua.javarush.mentor.services.TelegramService;
import com.ua.javarush.mentor.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static com.ua.javarush.mentor.exceptions.GeneralExceptionUtils.createGeneralException;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final String USER_CONFIRM_PATH = "/user/email/confirm/";
    private static final String SLASH = "/";
    private static final String TOKEN_EXPIRED = "Token expired";
    private static final String TOKEN_IS_NOT_VALID = "Token is not valid";
    private static final String USER_WITH_EMAIL = "User with email ";
    private static final String NOT_FOUND = " not found";
    public static final String LOG_CHANGE_PERMISSION_USER_TO = "Change permission user {} {} to {}";
    public static final String LOG_RESPONSE_USER = "Response user: {} {}";
    public static final String LOG_USER_WAS_CREATED = "User '{} {}' was created";
    public static final String LOG_REMOVE_USER_ID_NAME = "Remove user: id={}, name={} {}";
    public static final String NOT_FOUND_USER_ERROR = "Didn't found user";
    private static final int TIME_TO_CONFIRM_EMAIL = 30;
    @Value("${app.host}")
    private String host;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final TelegramService telegramService;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleService roleService, TelegramService telegramService, EmailService emailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.telegramService = telegramService;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public UserDTO createUser(UserCommand userCommand) throws GeneralException {
        User newUser = userMapper.mapToEntity(userCommand);
        newUser.setSecretPhrase(generateSecretPhrase());
        userRepository.save(newUser);
        log.info(LOG_USER_WAS_CREATED, newUser.getFirstName(), newUser.getLastName());
        return userMapper.mapToDto(newUser);
    }

    @Override
    public PageDTO<UserDTO> getAllUsers(int page, int size, String sortBy) {
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
        Page<UserDTO> users = userRepository.findAll(paging)
                .map(userMapper::mapToDto);
        return new PageDTO<>(users, paging);
    }

    @Override
    public UserDTO getUserById(Long id) throws GeneralException {
        User user = fetchUser(id);
        log.info(LOG_RESPONSE_USER, user.getFirstName(), user.getLastName());
        return userMapper.mapToDto(user);
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public void removeUser(Long id) throws GeneralException {
        User user = fetchUser(id);
        userRepository.deleteById(id);
        log.info(LOG_REMOVE_USER_ID_NAME, id, user.getFirstName(), user.getLastName());
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public void changePermission(UserPermissionCommand userPermissionCommand) throws GeneralException {
        User user = fetchUser(userPermissionCommand.getUserId());
        Role role = roleService.fetchRole(userPermissionCommand.getRoleId());
        user.setRoleId(role);
        userRepository.save(user);
        log.info(LOG_CHANGE_PERMISSION_USER_TO, user.getFirstName(), user.getLastName(), role.getName());
    }

    @Override
    public void sendMessage(UserMessageCommand userMessageCommand) throws GeneralException {
        User user = fetchUser(userMessageCommand.getUserId());
        log.info("Send message '{}' to user: {} {}", userMessageCommand.getMessage(), user.getFirstName(), user.getLastName());
        if (user.getTelegramId() != null) {
            telegramService.sendMessage(user.getTelegramId(), userMessageCommand.getMessage());
        } else {
            throw createGeneralException("Telegram id not set for user with id " + user.getId(), HttpStatus.BAD_REQUEST, Error.TELEGRAM_ID_NOT_FOUND);
        }
    }

    @Override
    public void sendConfirmationEmail(String email) throws GeneralException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> createGeneralException(USER_WITH_EMAIL + email + NOT_FOUND, HttpStatus.NOT_FOUND, Error.USER_NOT_FOUND));
        emailService.sendConfirmationEmail(createSendEmailCommand(user, AppLocale.EN));
        userRepository.save(user);
        log.info("Confirmation email was sent to user: {} {}", user.getFirstName(), user.getLastName());
    }

    @Override
    public void confirmEmail(String token, String email) throws GeneralException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> createGeneralException(USER_WITH_EMAIL + email + NOT_FOUND, HttpStatus.NOT_FOUND, Error.USER_NOT_FOUND));
        if (bCryptPasswordEncoder.matches(token, user.getEmailConfirmationToken())) {
            if (Date.from(addTimeInMinutesToDate(user.getDateOfSendingEmailConfirmation(),
                    TIME_TO_CONFIRM_EMAIL).toInstant()).after(new Date())) {
                user.setEmailVerified(true);
                user.setEmailConfirmationToken(null);
                user.setDateOfConfirmationEmail(new Date());
                user.setDateOfSendingEmailConfirmation(null);
                userRepository.save(user);
                log.info("User {} {} confirmed email", user.getFirstName(), user.getLastName());
            } else {
                throw createGeneralException(TOKEN_EXPIRED, HttpStatus.BAD_REQUEST, Error.TOKEN_EXPIRED);
            }
        } else {
            throw createGeneralException(TOKEN_IS_NOT_VALID, HttpStatus.BAD_REQUEST, Error.TOKEN_NOT_VALID);
        }
    }

    private String generateSecretPhrase() {
        return UUID.randomUUID().toString();
    }

    private SendEmailCommand createSendEmailCommand(User user, AppLocale appLocale) {
        return emailService.buildEmail(user.getEmail(), appLocale, EmailTemplates.CONFIRMATION,
                Map.of("confirmationLink", createConfirmationLink(user),
                        "firstName", user.getFirstName()));
    }

    private String createConfirmationLink(User user) {
        String emailToken = UUID.randomUUID().toString();
        user.setEmailConfirmationToken(bCryptPasswordEncoder.encode(emailToken));
        user.setDateOfSendingEmailConfirmation(new Date());
        return host + USER_CONFIRM_PATH + emailToken + SLASH + user.getEmail();
    }

    private Date addTimeInMinutesToDate(Date date, int timeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, timeInMinutes);
        return calendar.getTime();
    }

    @NotNull
    private User fetchUser(Long id) throws GeneralException {
        return userRepository.findById(id)
                .orElseThrow(() -> createGeneralException(NOT_FOUND_USER_ERROR, HttpStatus.NOT_FOUND, Error.USER_NOT_FOUND));
    }
}
