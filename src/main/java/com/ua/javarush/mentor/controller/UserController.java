package com.ua.javarush.mentor.controller;

import com.ua.javarush.mentor.command.UserCommand;
import com.ua.javarush.mentor.command.UserPermissionCommand;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Role API")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    @Operation(summary = "Create user",
            description = "Create user with userCommand",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = UserCommand.class)
                    )),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = UserDTO.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "User")
    public ResponseEntity<UserDTO> createNewRole(@RequestBody UserCommand userCommand) throws GeneralException {
        return new ResponseEntity<>(userService.createUser(userCommand), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "${default.pageSize}") int size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "${user.sortBy}") String sortBy
    ) {
        return new ResponseEntity<>(userService.getAllUsers(page, size, sortBy), HttpStatus.OK);
    @Operation(summary = "Get all users",
            description = "Get all users",
            parameters = {
                    @Parameter(name = "page", description = "Page number", required = true),
                    @Parameter(name = "size", description = "Page size", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "User")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                     @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return new ResponseEntity<>(userService.getAllUsers(page, size), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by id",
            description = "Get user by id",
            parameters = {
                    @Parameter(name = "userId", description = "User id", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = UserDTO.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "User")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Long userId) throws GeneralException {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user by id",
            description = "Delete user by id",
            parameters = {
                    @Parameter(name = "userId", description = "User id", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = UserDTO.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "User")
    public ResponseEntity<Void> removeUserById(@PathVariable("userId") Long userId) throws GeneralException {
        userService.removeUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/permission")
    @Operation(summary = "Add permission to user",
            description = "Add permission to user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = UserPermissionCommand.class)
                    )),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "User")
    public ResponseEntity<Void> changePermission(@RequestBody UserPermissionCommand userPermissionCommand) throws GeneralException {
        userService.changePermission(userPermissionCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/message")
    @Operation(summary = "Send message to user",
            description = "Send message to user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = UserMessageCommand.class)
                    )),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "User")
    public ResponseEntity<Void> sendMessage(@RequestBody UserMessageCommand userMessageCommand) throws GeneralException {
        userService.sendMessage(userMessageCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
