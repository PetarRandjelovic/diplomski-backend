package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.RoleDto;
import org.example.diplomski.data.entites.Role;
import org.springframework.stereotype.Component;


@Component
public class RoleMapper {
    public Role roleDtoToRole(RoleDto roleDto){
        return new Role(
            roleDto.getId(),
            roleDto.getRoleType()
        );
    }

    public RoleDto roleToRoleDto(Role role){
        return new RoleDto(
                role.getId(),
                role.getRoleType()
        );
    }
}
