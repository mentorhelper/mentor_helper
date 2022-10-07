package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.command.UserCommand;
import com.ua.javarush.mentor.command.UserMessageCommand;
import com.ua.javarush.mentor.command.UserPermissionCommand;
import com.ua.javarush.mentor.dto.PageDTO;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.exceptions.Error;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.mapper.UserMapper;
import com.ua.javarush.mentor.persist.model.Role;
import com.ua.javarush.mentor.persist.model.User;
import com.ua.javarush.mentor.persist.repository.UserRepository;
import com.ua.javarush.mentor.services.RoleService;
import com.ua.javarush.mentor.services.TelegramService;
import com.ua.javarush.mentor.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.ua.javarush.mentor.exceptions.GeneralExceptionUtils.createGeneralException;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    public static final String LOG_CHANGE_PERMISSION_USER_TO = "Change permission user {} {} to {}";
    public static final String LOG_RESPONSE_USER = "Response user: {} {}";
    public static final String LOG_USER_WAS_CREATED = "User '{} {}' was created";
    public static final String LOG_REMOVE_USER_ID_NAME = "Remove user: id={}, name={} {}";
    public static final String NOT_FOUND_USER_ERROR = "Didn't found user";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final TelegramService telegramService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleService roleService, TelegramService telegramService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.telegramService = telegramService;
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public UserDTO createUser(UserCommand userCommand) throws GeneralException {
        User newUser = userMapper.mapToEntity(userCommand);
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
        telegramService.sendMessage(user.getTelegramId(), userMessageCommand.getMessage());
    }

    @NotNull
    private User fetchUser(Long id) throws GeneralException {
        return userRepository.findById(id)
                .orElseThrow(() -> createGeneralException(NOT_FOUND_USER_ERROR, HttpStatus.NOT_FOUND, Error.USER_NOT_FOUND));
    }
}
