package com.ua.javaruh.mentor.controller;

import com.ua.javaruh.mentor.model.Role;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @GetMapping("")
    public Role getAllRole() {
        return new Role(1, "allroles");
    }

    @GetMapping("/{roleId}")
    public Role getRoleName() {
        //find role by id
        return new Role(1, "getRoleName");
    }

    @GetMapping("/{roleId}/permission")
    public Role getRolePermission() {
        //find role permission by id and return
        return new Role(2, "getRolePermission");
    }

    @PostMapping("/{roleId}")
    public Role createNewRole() {
        //Create new role
        return new Role(3, "createNewRole");
    }

    @PostMapping("/{roleId}/permission")
    public Role addPermissionToRole() {
        //Create new permission for role
        return new Role(4, "addPermission");
    }

    @DeleteMapping("/{roleId}")
    public Role removeRole() {
        //remove role
        return new Role(5, "removeRole");
    }

    @DeleteMapping("/{roleId}/permission")
    public Role removePermissionInRole() {
        //remove permission in role
        return new Role(6, "removePermissionInRole");
    }
}
