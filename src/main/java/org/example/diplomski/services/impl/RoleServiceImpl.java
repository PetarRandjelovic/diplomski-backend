package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.RoleDto;
import org.example.diplomski.data.entites.Role;
import org.example.diplomski.mapper.RoleMapper;
import org.example.diplomski.repositories.RoleRepository;
import org.example.diplomski.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDto> getAllRoles() {
        try{
            List<Role> roles=roleRepository.findAll();
            if(roles.isEmpty()) throw new RuntimeException("Role repository is empty.");

            return roles.stream().map(this.roleMapper::roleToRoleDto).collect(Collectors.toList());
        } catch(Exception e){
            throw new RuntimeException("Roles not retrieved.", e);
        }
    }
}