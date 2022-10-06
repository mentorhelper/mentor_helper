package com.ua.javarush.mentor.controller;

import com.ua.javarush.mentor.command.UserPermissionCommand;
import com.ua.javarush.mentor.command.UserCommand;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> createNewRole(@RequestBody UserCommand userCommand) throws GeneralException {
        return new ResponseEntity<>(userService.createUser(userCommand), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return new ResponseEntity<>(userService.getAllUsers(page), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Long userId) throws GeneralException {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PostMapping("/remove/{userId}")
    public ResponseEntity<UserDTO> removeUserById(@PathVariable("userId") Long userId) throws GeneralException {
        return new ResponseEntity<>(userService.removeUser(userId), HttpStatus.OK);
    }

    @PostMapping("/permission")
    public ResponseEntity<UserDTO> changePermission(@RequestBody UserPermissionCommand userPermissionCommand) throws GeneralException {
        return new ResponseEntity<>(userService.changePermission(userPermissionCommand), HttpStatus.OK);
    }
}
