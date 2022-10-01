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
    public Role getRoleName(@PathVariable Long roleId) {
        //find role by id
        return new Role(1, "getRoleName");
    }

    @GetMapping("/{roleId}/permission")
    public Role getRolePermission(@PathVariable Long roleId) {
        //find role permission by id and return
        return new Role(2, "getRolePermission");
    }

    @PostMapping("")
    public Role createNewRole() {
        //Create new role
        return new Role(3, "createNewRole");
    }

    @PostMapping("/{roleId}/permission")
    public Role addPermissionToRole(@PathVariable Long roleId) {
        //Create new permission for role
        return new Role(4, "addPermission");
    }

    @DeleteMapping("/{roleId}")
    public Role removeRole(@PathVariable Long roleId) {
        //remove role
        return new Role(5, "removeRole");
    }

    @DeleteMapping("/{roleId}/permission")
    public Role removePermissionInRole(@PathVariable Long roleId) {
        //remove permission in role
        return new Role(6, "removePermissionInRole");
    }
}
