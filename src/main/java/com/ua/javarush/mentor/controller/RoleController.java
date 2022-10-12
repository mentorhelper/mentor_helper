package com.ua.javarush.mentor.controller;

import com.ua.javarush.mentor.command.RoleCommand;
import com.ua.javarush.mentor.command.RoleToPermissionCommand;
import com.ua.javarush.mentor.dto.ErrorDTO;
import com.ua.javarush.mentor.dto.PageDTO;
import com.ua.javarush.mentor.dto.RoleDTO;
import com.ua.javarush.mentor.dto.RoleToPermissionDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.services.RoleService;
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

@RestController
@RequestMapping("/role")
@Tag(name = "Role", description = "Role API")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("")
    @Operation(summary = "Create role",
            description = "Create role with roleCommand",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = RoleCommand.class)
                    )),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = RoleDTO.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "Role")
    public ResponseEntity<RoleDTO> createNewRole(@RequestBody RoleCommand roleCommand) {
        return new ResponseEntity<>(roleService.createRole(roleCommand), HttpStatus.CREATED);
    }

    @GetMapping("")
    @Operation(summary = "Get all roles",
            description = "Get all roles",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = RoleDTO.class))
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "Role")
    public ResponseEntity<PageDTO<RoleDTO>> getAllRole(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "${default.pageSize}") int size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "${role.sortBy}") String sortBy
    ) {
        return new ResponseEntity<>(roleService.getAllRoles(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    @Operation(summary = "Get role by id",
            description = "Get role by id",
            parameters = {
                    @Parameter(name = "roleId", description = "Role id", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = RoleDTO.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "Role")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long roleId) throws GeneralException {
        return new ResponseEntity<>(roleService.getRoleById(roleId), HttpStatus.OK);
    }

    @GetMapping("/{roleId}/permission")
    @Operation(summary = "Get all permissions by role id",
            description = "Get all permissions by role id",
            parameters = {
                    @Parameter(name = "roleId", description = "Role id", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = RoleToPermissionDTO.class))
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "Role")
    public ResponseEntity<RoleToPermissionDTO> getRolePermission(@PathVariable Long roleId) throws GeneralException {
        return new ResponseEntity<>(roleService.getRolePermissionById(roleId), HttpStatus.OK);
    }

    @PostMapping("/{roleId}/permission")
    @Operation(summary = "Add permission to role",
            description = "Add permission to role",
            parameters = {
                    @Parameter(name = "roleId", description = "Role id", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = RoleToPermissionDTO.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "Role")
    public ResponseEntity<RoleToPermissionDTO> addPermissionToRole(@PathVariable Long roleId, @RequestBody RoleToPermissionCommand roleToPermissionCommand) throws GeneralException {
        return new ResponseEntity<>(roleService.addPermissionToRole(roleId, roleToPermissionCommand), HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}")
    @Operation(summary = "Delete role by id",
            description = "Delete role by id",
            parameters = {
                    @Parameter(name = "roleId", description = "Role id", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = RoleDTO.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "Role")
    public ResponseEntity<Void> removeRole(@PathVariable Long roleId) throws GeneralException {
        roleService.removeRole(roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{roleId}/permission")
    @Operation(summary = "Delete permission from role",
            description = "Delete permission from role",
            parameters = {
                    @Parameter(name = "roleId", description = "Role id", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = RoleDTO.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ))},
            tags = "Role")
    public ResponseEntity<Void> removePermissionInRole(@PathVariable Long roleId, @RequestBody RoleToPermissionCommand roleToPermissionCommand) throws GeneralException {
        roleService.removePermissionInRole(roleId, roleToPermissionCommand);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
