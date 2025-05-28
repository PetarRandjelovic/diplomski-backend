package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.user.CreateUserRecord;
import org.example.diplomski.data.dto.user.UserDto;
import org.example.diplomski.data.entites.Role;
import org.example.diplomski.data.entites.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole().getRoleType());
        return userDto;
    }

    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setRole(new Role(userDto.getRole()));
        return user;
    }

    public User toEntityFromRecord(CreateUserRecord createUserRecord) {
        User user = new User();
        user.setId(createUserRecord.id());
        user.setDateOfBirth(createUserRecord.dateOfBirth());
        user.setEmail(createUserRecord.email());
        user.setUsername(createUserRecord.username());
        user.setRole(new Role(createUserRecord.role()));
        return user;
    }
}