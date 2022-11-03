package com.ua.javarush.mentor.controller;

import com.ua.javarush.mentor.command.ModuleCommand;
import com.ua.javarush.mentor.dto.ErrorDTO;
import com.ua.javarush.mentor.dto.ModuleDTO;
import com.ua.javarush.mentor.dto.PageDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.services.ModuleService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modules")
@Tag(name = "Modules", description = "Modules API")
public class ModuleController {
    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create module",
            description = "Create module with moduleCommand",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ModuleCommand.class)
                    )),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = ModuleDTO.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "Modules")
    public ResponseEntity<ModuleDTO> createModule(@RequestBody ModuleCommand moduleCommand) {
        return new ResponseEntity<>(moduleService.createModule(moduleCommand), HttpStatus.CREATED);
    }

    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get all modules",
            description = "Get all modules",
            parameters = {
                    @Parameter(name = "page", description = "Page number", required = true),
                    @Parameter(name = "size", description = "Page size", required = true),
                    @Parameter(name = "sort", description = "Sort by field", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = ModuleDTO.class))
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "Modules")
    public ResponseEntity<PageDTO<ModuleDTO>> getAllModules(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "${default.pageSize}") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "${user.sortBy}") String sortBy) {
        return new ResponseEntity<>(moduleService.getAllModules(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("/{moduleId}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get module by id",
            description = "Get module by id",
            parameters = {
                    @Parameter(name = "moduleId", description = "Module id", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = ModuleDTO.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "Modules")
    public ResponseEntity<ModuleDTO> getModuleById(@PathVariable("moduleId") Long moduleId) throws GeneralException {
        return new ResponseEntity<>(moduleService.getModuleById(moduleId), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get module by name",
            description = "Get module by name",
            parameters = {
                    @Parameter(name = "name", description = "Name", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = ModuleDTO.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "Modules")
    public ResponseEntity<ModuleDTO> getModuleByName(@PathVariable("name") String name) throws GeneralException {
        return new ResponseEntity<>(moduleService.getModuleByName(name), HttpStatus.OK);
    }

    @PutMapping("/{moduleId}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update module",
            description = "Update module with moduleCommand by moduleId",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ModuleCommand.class)
                    )),
            parameters = {
                    @Parameter(name = "moduleId", description = "Module id", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = ModuleDTO.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "Modules")
    public ResponseEntity<ModuleDTO> updateModule(@RequestBody ModuleCommand moduleCommand,
                                                  @PathVariable("moduleId") Long moduleId) throws GeneralException {
        return new ResponseEntity<>(moduleService.updateModule(moduleCommand, moduleId), HttpStatus.OK);
    }

    @DeleteMapping("/{moduleId}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete module by id",
            description = "Delete module by id",
            parameters = {
                    @Parameter(name = "moduleId", description = "Module id", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = ModuleDTO.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "Modules")
    public ResponseEntity<Void> deleteModule(@PathVariable("moduleId") Long moduleId) throws GeneralException {
        moduleService.removeModule(moduleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
