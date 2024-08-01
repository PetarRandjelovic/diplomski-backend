package org.example.diplomski.services;

import org.example.diplomski.data.dto.RoleDto;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface RoleService {
    List<RoleDto> getAllRoles();


}
