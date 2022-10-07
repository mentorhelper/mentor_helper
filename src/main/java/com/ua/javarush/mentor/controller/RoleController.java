package com.ua.javarush.mentor.controller;

import com.ua.javarush.mentor.command.RoleCommand;
import com.ua.javarush.mentor.dto.RoleDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.persist.model.Role;
import com.ua.javarush.mentor.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("")
    public ResponseEntity<RoleDTO> createNewRole(@RequestBody RoleCommand roleCommand) {
        return new ResponseEntity<>(roleService.createRole(roleCommand), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllRole(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "${default.pageSize}") int size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "${role.sortBy}") String sortBy
    ) {
        return new ResponseEntity<>(roleService.getAllRoles(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long roleId) throws GeneralException {
        return new ResponseEntity<>(roleService.getRoleById(roleId), HttpStatus.OK);
    }

    @GetMapping("/{roleId}/permission")
    public ResponseEntity<RoleDTO> getRolePermission(@PathVariable Long roleId) throws GeneralException {
        return new ResponseEntity<>(roleService.getRolePermissionById(roleId), HttpStatus.OK);
    }

    @PostMapping("/{roleId}/permission")
    public ResponseEntity<RoleDTO> addPermissionToRole(@PathVariable Long roleId) throws GeneralException {
        return new ResponseEntity<>(roleService.addPermissionToRole(roleId), HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<RoleDTO> removeRole(@PathVariable Long roleId) throws GeneralException {
        return new ResponseEntity<>(roleService.removeRole(roleId), HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}/permission")
    public ResponseEntity<RoleDTO> removePermissionInRole(@PathVariable Long roleId) throws GeneralException {
        return new ResponseEntity<>(roleService.removePermissionInRole(roleId), HttpStatus.OK);
    }
}
