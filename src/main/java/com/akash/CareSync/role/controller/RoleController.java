package com.akash.CareSync.role.controller;

import com.akash.CareSync.role.entity.Role;
import com.akash.CareSync.role.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rest/v1/role")
public class RoleController {
    private final RoleService roleService;

    RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<Role> getRoleList() {
        return roleService.getAllRoles();
    }

    @PostMapping
    public Role addRole(@RequestBody Role role) {
        return roleService.addRole(role);
    }
}
