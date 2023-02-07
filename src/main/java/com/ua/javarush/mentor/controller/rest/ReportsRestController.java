package com.ua.javarush.mentor.controller.rest;

import com.ua.javarush.mentor.dto.ErrorDTO;
import com.ua.javarush.mentor.enums.AppLocale;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/reports")
@Tag(name = "Reports", description = "Reports API")
public class ReportsRestController {

    private final UserService userService;

    @Autowired
    public ReportsRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/export/pdf")
    @Operation(
            summary = "Generate pdf",
            description = "Generate pdf about all users. Available languages: EN, RU",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "Reports")
    public ResponseEntity<Void> exportToPDF(HttpServletResponse response,
                                            AppLocale appLocale) throws GeneralException {
        userService.exportToPDF(response, appLocale);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
