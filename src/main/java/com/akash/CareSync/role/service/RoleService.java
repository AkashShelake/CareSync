package com.akash.CareSync.role.service;

import com.akash.CareSync.role.entity.Role;
import com.akash.CareSync.role.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class RoleService {
    RoleRepository roleRepository;
    RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    @PostMapping
    public Role addRole(@RequestBody Role role) {
        return roleRepository.save(role);
    }

}
