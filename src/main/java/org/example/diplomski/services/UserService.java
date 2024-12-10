package org.example.diplomski.services;

import org.example.diplomski.data.dto.UserDto;
import org.example.diplomski.data.entites.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    UserDto findById(Long id);

    UserDto findByEmail(String email);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    Integer deleteUserByEmail(String email);


}
