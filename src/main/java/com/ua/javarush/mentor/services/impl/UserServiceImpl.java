package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.command.*;
import com.ua.javarush.mentor.dto.PageDTO;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.enums.AppLocale;
import com.ua.javarush.mentor.exceptions.UiError;
import com.ua.javarush.mentor.enums.Configs;
import com.ua.javarush.mentor.enums.EmailTemplates;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.mapper.UserDetailsMapper;
import com.ua.javarush.mentor.mapper.UserMapper;
import com.ua.javarush.mentor.persist.model.Role;
import com.ua.javarush.mentor.persist.model.User;
import com.ua.javarush.mentor.persist.repository.ConfigRepository;
import com.ua.javarush.mentor.persist.repository.UserRepository;
import com.ua.javarush.mentor.reports.UserPDFExporter;
import com.ua.javarush.mentor.services.RoleService;
import com.ua.javarush.mentor.services.TelegramService;
import com.ua.javarush.mentor.services.UserService;
import com.ua.javarush.mentor.services.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import static com.ua.javarush.mentor.enums.PDFSubtype.USERS;
import static com.ua.javarush.mentor.exceptions.GeneralExceptionUtils.createGeneralException;

@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final String USER_CONFIRM_PATH = "/api/user/email/confirm/";
    private static final String SLASH = "/";
    private static final String TOKEN_EXPIRED = "Token expired";
    private static final String CODE_EXPIRED = "Code expired";
    private static final String TOKEN_IS_NOT_VALID = "Token is not valid";
    private static final String CODE_IS_NOT_VALID = "Code is not valid";
    private static final String USER_WITH_EMAIL = "User with email ";
    private static final String NOT_FOUND = " not found";
    public static final String LOG_CHANGE_PERMISSION_USER_TO = "Change permission user {} {} to {}";
    public static final String LOG_RESPONSE_USER = "Response user: {} {}";
    public static final String LOG_USER_WAS_CREATED = "User '{} {}' was created";
    public static final String LOG_REMOVE_USER_ID_NAME = "Remove user: id={}, name={} {}";
    public static final String NOT_FOUND_USER_ERROR = "Didn't found user";
    private static final String PDF_FORMAT = ".pdf";
    private static final String DATETIME_PATTERN_FOR_REPORT = "yyyy-MM-dd_HH:mm:ss";
    private static final String PDF_REPORT_SORT_TYPE_DEFAULT = "firstName";
    private static final String CONTENT_TYPE_PDF = "application/pdf";
    public static final String INVALID_EMAIL = "Invalid email";
    public static final String INVALID_PASSWORD = "Invalid password";
    public static final String INVALID_USERNAME = "Invalid username";
    public static final String COUNTRY_IS_NOT_SUPPORTED_PLEASE_CHOOSE_OTHER_COUNTRY = "Country is not supported. Please choose other country";
    public static final String COUNTRY_IS_NOT_SET = "Country is not set";
    public static final String USERNAME_ALREADY_EXISTS = "Username already exists";

    @Value("${app.host}")
    private String host;

    private final UserRepository userRepository;
    private final ConfigRepository configRepository;
    private final UserMapper userMapper;
    private final UserDetailsMapper userDetailsMapper;
    private final RoleService roleService;
    private final ValidationService validationService;
    private final TelegramService telegramService;
    private final UserPDFExporter userPDFExporter;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, ConfigRepository configRepository, UserMapper userMapper, UserDetailsMapper userDetailsMapper, UserPDFExporter userPDFExporter, RoleService roleService, ValidationService validationService, TelegramService telegramService, EmailService emailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.configRepository = configRepository;
        this.userMapper = userMapper;
        this.userDetailsMapper = userDetailsMapper;
        this.roleService = roleService;
        this.validationService = validationService;
        this.telegramService = telegramService;
        this.userPDFExporter = userPDFExporter;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserDetails userDetails = userDetailsMapper.mapToUserDetails(user.get());
        new AccountStatusUserDetailsChecker().check(userDetails);
        return userDetails;
    }

    @Override
    public void matchPassword(User user, String password) throws GeneralException {
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw createGeneralException(INVALID_PASSWORD, HttpStatus.BAD_REQUEST, UiError.PASSWORD_NOT_VALID);
        }
    }

    @Override
    public User findUserByUsername(String username) throws GeneralException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw createGeneralException("User not found", HttpStatus.NOT_FOUND, UiError.USER_NOT_FOUND);
        }
        return user.get();
    }

    @Override
    public User findUserByEmail(String email) throws GeneralException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> createGeneralException(NOT_FOUND_USER_ERROR, HttpStatus.NOT_FOUND, UiError.USER_NOT_FOUND));
    }

    @Override
    public UserDetails loadUserDetailsByUserId(Long id) throws GeneralException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> createGeneralException(NOT_FOUND_USER_ERROR, HttpStatus.NOT_FOUND, UiError.USER_NOT_FOUND));
        return userDetailsMapper.mapToUserDetails(user);
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public UserDTO createUser(UserCommand userCommand) throws GeneralException {
        User newUser = userMapper.mapToEntity(userCommand);
        validateUserData(newUser);
        addSecurityData(newUser);
        userRepository.save(newUser);
        log.info(LOG_USER_WAS_CREATED, newUser.getFirstName(), newUser.getLastName());
        sendConfirmationEmail(newUser.getEmail());
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
            throw createGeneralException("Telegram id not set for user with id " + user.getId(), HttpStatus.BAD_REQUEST, UiError.TELEGRAM_ID_NOT_FOUND);
        }
    }

    @Override
    public void exportToPDF(HttpServletResponse response, AppLocale appLocale) throws GeneralException {
        response.setContentType(CONTENT_TYPE_PDF);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + generateFileName(USERS);
        response.setHeader(headerKey, headerValue);

        userPDFExporter.export(response,
                userRepository.findAll(Sort.by(PDF_REPORT_SORT_TYPE_DEFAULT).ascending())
                        .stream()
                        .map(userMapper::mapToDto)
                        .collect(Collectors.toList()),
                appLocale);
    }

    @Override
    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void sendConfirmationEmail(String email) throws GeneralException {
        User user = findUserByEmail(email);
        emailService.sendConfirmationEmail(createSendEmailCommand(user, AppLocale.EN));
        userRepository.save(user);
        log.info("Confirmation email was sent to user: {} {}", user.getFirstName(), user.getLastName());
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class, propagation = Propagation.REQUIRES_NEW)
    public void confirmEmail(String token, String email) throws GeneralException {
        User user = findUserByEmail(email);
        if (bCryptPasswordEncoder.matches(token, user.getEmailConfirmationToken())) {
            if (Date.from(addTimeInMinutesToDate(user.getDateOfSendingEmailConfirmation(),
                    Integer.parseInt(configRepository.findByName(Configs.TIME_TO_CONFIRM_EMAIL.name()).getValue())).toInstant()).after(new Date())) {
                user.setEmailVerified(true);
                user.setEnabled(true);
                user.setEmailConfirmationToken(null);
                user.setDateOfConfirmationEmail(new Date());
                user.setDateOfSendingEmailConfirmation(null);
                userRepository.save(user);
                log.info("User {} {} confirmed email", user.getFirstName(), user.getLastName());
            } else {
                throw createGeneralException(TOKEN_EXPIRED, HttpStatus.BAD_REQUEST, UiError.TOKEN_EXPIRED);
            }
        } else {
            throw createGeneralException(TOKEN_IS_NOT_VALID, HttpStatus.BAD_REQUEST, UiError.TOKEN_NOT_VALID);
        }
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class, propagation = Propagation.REQUIRES_NEW)
    public void resetPassword(String email) throws GeneralException {
        User user = findUserByEmail(email);
        if (availableToChangePassword(user)) {
            Integer code = generateSixDigitCode();
            user.setResetPasswordCode(bCryptPasswordEncoder.encode(String.valueOf(code)));
            user.setDateOfResetPassword(new Date());
            user.setCountOfResetPassword(incrementAndGet(user));
            userRepository.save(user);
            emailService.sendResetPasswordEmail(createSendResetPasswordEmailCommand(user, AppLocale.EN, code));
            log.info("Reset password email was sent to user: {} {}", user.getFirstName(), user.getLastName());
        } else {
            throw createGeneralException("Max count of reset password reached", HttpStatus.BAD_REQUEST, UiError.MAX_COUNT_OF_RESET_PASSWORD_REACHED);
        }
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class, propagation = Propagation.REQUIRES_NEW)
    public void changePassword(ChangePasswordCommand changePasswordCommand, Principal principal) throws GeneralException {
        User user = getUserByPrincipal(principal);
        if (bCryptPasswordEncoder.matches(changePasswordCommand.getOldPassword(), user.getPassword())) {
            if (validationService.isValidPassword(changePasswordCommand.getNewPassword())) {
                user.setPassword(bCryptPasswordEncoder.encode(changePasswordCommand.getNewPassword()));
                user.setLastPasswordChange(new Date());
                userRepository.save(user);
                log.info("Password was changed for user: {} {}", user.getFirstName(), user.getLastName());
            } else {
                throw createGeneralException("New password is not valid", HttpStatus.BAD_REQUEST, UiError.PASSWORD_NOT_VALID);
            }
        } else {
            throw createGeneralException("Old password is not valid", HttpStatus.BAD_REQUEST, UiError.OLD_PASSWORD_NOT_VALID);
        }
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class, propagation = Propagation.REQUIRES_NEW)
    public void confirmResetPassword(ResetPasswordCommand resetPasswordCommand) throws GeneralException {
        User user = findUserByEmail(resetPasswordCommand.getEmail());
        if (bCryptPasswordEncoder.matches(resetPasswordCommand.getCode(), user.getResetPasswordCode())) {
            if (Date.from(addTimeInMinutesToDate(user.getDateOfResetPassword(),
                    Integer.parseInt(configRepository.findByName(Configs.TIME_TO_RESET_PASSWORD.name()).getValue())).toInstant()).after(new Date())) {
                if (validationService.isValidPassword(resetPasswordCommand.getNewPassword())) {
                    user.setPassword(bCryptPasswordEncoder.encode(resetPasswordCommand.getNewPassword()));
                    user.setResetPasswordCode(null);
                    user.setCountOfResetPassword(0);
                    user.setDateOfSendingResetPassword(null);
                    user.setLastPasswordChange(new Date());
                    user.setDateOfResetPassword(new Date());
                    userRepository.save(user);
                    log.info("Password was reset for user: {} {}", user.getFirstName(), user.getLastName());
                } else {
                    throw createGeneralException("New password is not valid", HttpStatus.BAD_REQUEST, UiError.PASSWORD_NOT_VALID);
                }
            } else {
                throw createGeneralException(CODE_EXPIRED, HttpStatus.BAD_REQUEST, UiError.CODE_EXPIRED);
            }
        } else {
            throw createGeneralException(CODE_IS_NOT_VALID, HttpStatus.BAD_REQUEST, UiError.CODE_NOT_VALID);
        }
    }

    private User getUserByPrincipal(Principal principal) throws GeneralException {
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> createGeneralException(USER_WITH_EMAIL + principal.getName() + NOT_FOUND, HttpStatus.NOT_FOUND, UiError.USER_NOT_FOUND));
    }

    private boolean availableToChangePassword(User user) {
        if (user.getCountOfResetPassword() == null) {
            return true;
        } else {
            return user.getCountOfResetPassword() < Long.parseLong(configRepository.findByName(Configs.MAX_COUNT_OF_RESET_PASSWORD.name()).getValue());
        }
    }

    private void addSecurityData(User newUser) {
        newUser.setSecretPhrase(generateSecretPhrase());
        newUser.setEnabled(false);
        newUser.setLocked(false);
    }

    private Integer incrementAndGet(User user) {
        return user.getCountOfResetPassword() == null ? 1 : user.getCountOfResetPassword() + 1;
    }

    private String generateFileName(String filePrefix) {
        DateFormat dateFormatter = new SimpleDateFormat(DATETIME_PATTERN_FOR_REPORT);
        String currentDateTime = dateFormatter.format(new Date());
        return filePrefix + currentDateTime + PDF_FORMAT;
    }

    private String generateSecretPhrase() {
        return UUID.randomUUID().toString();
    }

    private SendEmailCommand createSendEmailCommand(User user, AppLocale appLocale) {
        return emailService.buildEmail(user.getEmail(), appLocale, EmailTemplates.CONFIRMATION,
                Map.of("confirmationLink", createConfirmationLink(user),
                        "firstName", user.getFirstName()));
    }

    private SendEmailCommand createSendResetPasswordEmailCommand(User user, AppLocale appLocale, Integer code) {
        return emailService.buildEmail(user.getEmail(), appLocale, EmailTemplates.RESET_PASSWORD,
                Map.of("resetPasswordCode", String.valueOf(code),
                        "firstName", user.getFirstName()));
    }

    private Integer generateSixDigitCode() {
        return RandomUtils.nextInt(100000, 999999);
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

    private void validateUserData(User newUser) throws GeneralException {
        validatePassword(newUser);
        validateEmail(newUser);
        validateUsername(newUser);
        validateCountry(newUser);
    }

    private void validateCountry(User newUser) throws GeneralException {
        if (newUser.getCountry() == null) {
            throw createGeneralException(COUNTRY_IS_NOT_SET, HttpStatus.BAD_REQUEST, UiError.COUNTRY_NOT_SET);
        }
        if (!validationService.isValidCountry(newUser.getCountry())) {
            throw createGeneralException(COUNTRY_IS_NOT_SUPPORTED_PLEASE_CHOOSE_OTHER_COUNTRY, HttpStatus.BAD_REQUEST, UiError.COUNTRY_NOT_FOUND);
        }
    }

    private void validateUsername(User newUser) throws GeneralException {
        if (validationService.isValidUsername(newUser.getUsername())) {
            if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
                throw createGeneralException(USERNAME_ALREADY_EXISTS, HttpStatus.BAD_REQUEST, UiError.USERNAME_ALREADY_EXISTS);
            }
            newUser.setUsername(newUser.getUsername().toLowerCase());
        } else {
            throw createGeneralException(INVALID_USERNAME, HttpStatus.BAD_REQUEST, UiError.USERNAME_NOT_VALID);
        }
    }

    private void validateEmail(User newUser) throws GeneralException {
        if (validationService.isValidEmail(newUser.getEmail())) {
            if (isEmailExists(newUser.getEmail())) {
                throw createGeneralException(USER_WITH_EMAIL + newUser.getEmail() + " already exists", HttpStatus.BAD_REQUEST, UiError.USER_EMAIL_ALREADY_EXISTS);
            }
            newUser.setEmail(newUser.getEmail().toLowerCase());
        } else {
            throw createGeneralException(INVALID_EMAIL, HttpStatus.BAD_REQUEST, UiError.EMAIL_NOT_VALID);
        }
    }

    private void validatePassword(User newUser) throws GeneralException {
        if (validationService.isValidPassword(newUser.getPassword())) {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        } else {
            throw createGeneralException(INVALID_PASSWORD, HttpStatus.BAD_REQUEST, UiError.PASSWORD_NOT_VALID);
        }
    }

    @NotNull
    private User fetchUser(Long id) throws GeneralException {
        return userRepository.findById(id)
                .orElseThrow(() -> createGeneralException(NOT_FOUND_USER_ERROR, HttpStatus.NOT_FOUND, UiError.USER_NOT_FOUND));
    }
}
