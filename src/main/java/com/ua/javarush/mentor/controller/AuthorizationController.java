package com.ua.javarush.mentor.controller;

import com.ua.javarush.mentor.command.LoginCommand;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.enums.DeviceType;
import com.ua.javarush.mentor.exceptions.Error;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.services.AuthorizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization", description = "Auth API")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login",
            description = "Login user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login success", content =
                    @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = Error.class)))
            },
            tags = "Authorization")
    public ResponseEntity<UserDTO> login(@RequestHeader(value = "APP_DEVICE_TYPE", required = false, defaultValue = "WEB") DeviceType deviceType,
                                         @RequestBody @Valid LoginCommand loginCommand) throws GeneralException {
        UserDTO user = authorizationService.login(loginCommand.getEmail(), loginCommand.getPassword(), deviceType);
        HttpHeaders headers = setCookieRefreshToken(authorizationService.getRefreshToken(user.getId(), user.getSessionExpiredDate()));
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @PostMapping("/refresh-tokens")
    @Operation(summary = "Get new tokens",
            description = "Get new tokens",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get new tokens success", content =
                    @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    @Content(schema = @Schema(implementation = Error.class)))
            },
            tags = "Authorization")
    public ResponseEntity<UserDTO> refreshTokens(@RequestHeader(value = "APP_DEVICE_TYPE", required = false, defaultValue = "WEB") DeviceType deviceType,
                                                 @CookieValue("refresh_token") String refreshToken) throws GeneralException {

        UserDTO user = authorizationService.refreshTokens(deviceType, refreshToken);
        HttpHeaders headers = setCookieRefreshToken(authorizationService.getRefreshToken(user.getId(), user.getSessionExpiredDate()));
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    private HttpHeaders setCookieRefreshToken(UUID refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "refresh_token=" + refreshToken.toString() + "; Max-Age=864000; Path=/; SameSite=None; Secure; HttpOnly;");
        return headers;
    }
}
