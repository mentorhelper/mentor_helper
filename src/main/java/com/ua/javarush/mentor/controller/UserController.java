package com.ua.javarush.mentor.controller;

import com.ua.javarush.mentor.command.*;
import com.ua.javarush.mentor.dto.ErrorDTO;
import com.ua.javarush.mentor.dto.PageDTO;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.enums.AppLocale;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.persist.model.User;
import com.ua.javarush.mentor.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.security.Principal;

import static com.ua.javarush.mentor.exceptions.UiErrorCode.USER_EMAIL_ALREADY_EXISTS;


@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Role API")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
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
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCommand userCommand) throws GeneralException {
        return new ResponseEntity<>(userService.createUser(userCommand), HttpStatus.CREATED);
    }

    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get all users",
            description = "Get all users",
            parameters = {
                    @Parameter(name = "page", description = "Page number", required = true),
                    @Parameter(name = "size", description = "Page size", required = true),
                    @Parameter(name = "sort", description = "Sort by field", required = true)
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
    public ResponseEntity<PageDTO<UserDTO>> getAllUsers(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "${default.pageSize}") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "${user.sortBy}") String sortBy) {
        return new ResponseEntity<>(userService.getAllUsers(page, size, sortBy), HttpStatus.OK);
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
    @SecurityRequirement(name = "bearerAuth")
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
    @SecurityRequirement(name = "bearerAuth")
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
    @SecurityRequirement(name = "bearerAuth")
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


    @GetMapping("/{email}/confirmation")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Send confirmation email",
            description = "Send confirmation email",
            parameters = {
                    @Parameter(name = "email", description = "User email", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "User")
    public ResponseEntity<Void> sendConfirmationEmail(@PathVariable("email") String email) throws GeneralException {
        userService.sendConfirmationEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/email/confirm/{token}/{email}")
    @Operation(summary = "Confirm email",
            description = "Confirm email",
            parameters = {
                    @Parameter(name = "token", description = "Token", required = true),
                    @Parameter(name = "email", description = "User email", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "User")
    public ResponseEntity<Void> confirmEmail(@PathVariable("token") String token,
                                             @PathVariable("email") String email) throws GeneralException {
        userService.confirmEmail(token, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/password/reset/{email}")
    @Operation(summary = "Reset password, ask email code",
            description = "Reset password",
            parameters = {
                    @Parameter(name = "email", description = "User email", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "User")
    public ResponseEntity<Void> resetPassword(@PathVariable("email") String email) throws GeneralException {
        userService.resetPassword(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/password/reset/confirm")
    @Operation(summary = "Reset password with code",
            description = "Reset password with code",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "User")
    public ResponseEntity<Void> resetPasswordWithCode(@RequestBody ResetPasswordCommand resetPasswordCommand) throws GeneralException {
        userService.confirmResetPassword(resetPasswordCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/password/change")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Change password",
            description = "Change password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "User")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordCommand changePasswordCommand, Principal principal) throws GeneralException {
        userService.changePassword(changePasswordCommand, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/export/pdf")
    @Operation(
            summary = "Generate pdf",
            description = "Generate pdf about all users. Available languages: EN, RU",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))}
    )
    public ResponseEntity<Void> exportToPDF(HttpServletResponse response,
                                            AppLocale appLocale) throws GeneralException {
        userService.exportToPDF(response, appLocale);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value= {"/", "/login"}, method=RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();

        model.setViewName("user/login");
        return model;
    }

    @RequestMapping(value= {"/signup"}, method=RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView model = new ModelAndView();
        User user = new User();
        model.addObject("user", user);
        model.setViewName("user/signup");

        return model;
    }

    @RequestMapping(value= {"/signup"}, method=RequestMethod.POST)
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult) throws GeneralException {
        ModelAndView model = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());

        if(userExists != null) {
            bindingResult.rejectValue("email", "error.user", USER_EMAIL_ALREADY_EXISTS);
        }
        if(bindingResult.hasErrors()) {
            model.setViewName("user/signup");
        } else {
            userService.saveUser(user);
            model.addObject("msg", "User has been registered successfully!");
            model.addObject("user", new User());
            model.setViewName("user/signup");
        }

        return model;
    }

    @RequestMapping(value= {"/home/home"}, method=RequestMethod.GET)
    public ModelAndView home() throws GeneralException {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        model.addObject("userName", user.getFirstName() + " " + user.getLastName());
        model.setViewName("home/home");
        return model;
    }

    @RequestMapping(value= {"/access_denied"}, method=RequestMethod.GET)
    public ModelAndView accessDenied() {
        ModelAndView model = new ModelAndView();
        model.setViewName("errors/access_denied");
        return model;
    }
}
