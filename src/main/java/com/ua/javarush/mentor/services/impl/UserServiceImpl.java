package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.controller.user.UserRequest;
import com.ua.javarush.mentor.dto.UserDTO;
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
    public UserDTO getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) {
            log.warn("Didn't found user with id {}", id);
            throw new IllegalArgumentException("Didn't found user");
        }
        log.info("Response user: {}", optionalUser.get());
        return userMapper.mapToDto(optionalUser.get());
    }

    @Override
    public UserDTO removeUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) {
            log.warn("Didn't found user with id {}", id);
            throw new IllegalArgumentException("Didn't found user");
        }
        log.info("Remove user: id={}, name={} {}", id, optionalUser.get().getFirstName(), optionalUser.get().getLastName());
        userRepository.deleteById(id);
        return userMapper.mapToDto(optionalUser.get());
    }

    @Override
    public UserDTO changePermission(Long id, Long roleId) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) {
            log.warn("Didn't found user with id {}", id);
            throw new IllegalArgumentException("Didn't found user");
        }
        User User = optionalUser.get();
        Role role = fetchRoleId(roleId);
        User.setRoleId(role);
        userRepository.save(User);
        log.info("Change permission user {} {} to {}", User.getFirstName(), User.getLastName(), role.getName());
        return userMapper.mapToDto(User);
    }

    @NotNull
    private Role fetchRoleId(Long roleId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalRole.isEmpty()) {
            log.warn("Didn't found role with id {}", roleId);
            throw new IllegalArgumentException("Didn't found role");
        }
        return optionalRole.get();
    }
}
