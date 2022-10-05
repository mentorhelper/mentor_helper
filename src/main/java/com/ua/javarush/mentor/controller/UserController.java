package com.ua.javarush.mentor.controller;

import com.ua.javarush.mentor.controller.user.UserPageableRequest;
import com.ua.javarush.mentor.controller.user.UserPermissionRequest;
import com.ua.javarush.mentor.controller.user.UserRequest;
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
    public ResponseEntity<UserDTO> createNewRole(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.create(userRequest), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> getAllUsersPageable(@RequestBody UserPageableRequest userPageableRequest) {
        return new ResponseEntity<>(userService.getAllUsers(userPageableRequest.getPage()), HttpStatus.OK);
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
    public ResponseEntity<UserDTO> changePermission(@RequestBody UserPermissionRequest userPermissionRequest) throws GeneralException {
        return new ResponseEntity<>(userService.changePermission(userPermissionRequest.getUserId(), userPermissionRequest.getRoleId()), HttpStatus.OK);
    }
}
