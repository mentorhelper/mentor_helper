package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.controller.user.UserRequest;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.exceptions.Error;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.exceptions.GeneralExceptionUtils;
import com.ua.javarush.mentor.mapper.UserMapper;
import com.ua.javarush.mentor.persist.model.User;
import com.ua.javarush.mentor.persist.model.Role;
import com.ua.javarush.mentor.persist.repository.RoleRepository;
import com.ua.javarush.mentor.persist.repository.UserRepository;
import com.ua.javarush.mentor.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Value("${user.pageSize}")
    private Integer PAGE_SIZE;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDTO create(UserRequest userRequest) {
        User newUser = userMapper.mapToEntity(userRequest);
        newUser.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
        System.out.println(newUser);
        userRepository.save(newUser);
        log.info("User '{} {}' was created", newUser.getFirstName(), newUser.getLastName());
        return userMapper.mapToDto(newUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsers(Integer page) {
        return userRepository.findAll(Pageable.ofSize(PAGE_SIZE).withPage(page))
                .stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) throws GeneralException {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) {
            log.warn("Didn't found user with id {}", id);
            throw GeneralExceptionUtils.createGeneralException("Didn't found user", HttpStatus.NOT_FOUND, Error.USER_NOT_FOUND);
        }
        log.info("Response user: {}", optionalUser.get());
        return userMapper.mapToDto(optionalUser.get());
    }

    @Override
    public UserDTO removeUser(Long id) throws GeneralException {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) {
            log.warn("Didn't found user with id {}", id);
            throw GeneralExceptionUtils.createGeneralException("Didn't found user", HttpStatus.NOT_FOUND, Error.USER_NOT_FOUND);
        }
        log.info("Remove user: id={}, name={} {}", id, optionalUser.get().getFirstName(), optionalUser.get().getLastName());
        userRepository.deleteById(id);
        return userMapper.mapToDto(optionalUser.get());
    }

    @Override
    public UserDTO changePermission(Long id, Long roleId) throws GeneralException {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) {
            log.warn("Didn't found user with id {}", id);
            throw GeneralExceptionUtils.createGeneralException("Didn't found user", HttpStatus.NOT_FOUND, Error.USER_NOT_FOUND);
        }
        User user = optionalUser.get();
        Role role = fetchRoleId(roleId);
        user.setRoleId(role);
        userRepository.save(user);
        log.info("Change permission user {} {} to {}", user.getFirstName(), user.getLastName(), role.getName());
        return userMapper.mapToDto(user);
    }

    @NotNull
    private Role fetchRoleId(Long roleId) throws GeneralException {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalRole.isEmpty()) {
            log.warn("Didn't found role with id {}", roleId);
            throw GeneralExceptionUtils.createGeneralException("Didn't found role", HttpStatus.NOT_FOUND, Error.ROLE_NOT_FOUND);
        }
        return optionalRole.get();
    }
}
