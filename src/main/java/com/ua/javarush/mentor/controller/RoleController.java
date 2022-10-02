package com.ua.javarush.mentor.controller;

import com.ua.javarush.mentor.command.RoleCommand;
import com.ua.javarush.mentor.dto.RoleDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.persist.model.Role;
import com.ua.javarush.mentor.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("")
    public ResponseEntity<RoleDTO> createNewRole(@RequestBody RoleCommand roleCommand) throws GeneralException {
        return new ResponseEntity<>(roleService.create(roleCommand), HttpStatus.CREATED);
    }

    @GetMapping("")
    public Role getAllRole() {
        return null;
    }

    @GetMapping("/{roleId}")
    public Role getRoleName(@PathVariable Long roleId) {
        //find role by id
        return null;
    }

    @GetMapping("/{roleId}/permission")
    public Role getRolePermission(@PathVariable Long roleId) {
        //find role permission by id and return
        return null;
    }

    @PostMapping("/{roleId}/permission")
    public Role addPermissionToRole(@PathVariable Long roleId) {
        //Create new permission for role
        return null;
    }

    @DeleteMapping("/{roleId}")
    public Role removeRole(@PathVariable Long roleId) {
        //remove role
        return null;
    }

    @DeleteMapping("/{roleId}/permission")
    public Role removePermissionInRole(@PathVariable Long roleId) {
        //remove permission in role
        return null;
    }
}
