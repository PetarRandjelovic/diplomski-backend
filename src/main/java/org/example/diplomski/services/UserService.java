package org.example.diplomski.services;

import org.example.diplomski.data.dto.CreateUserRecord;
import org.example.diplomski.data.dto.UserDto;
import org.example.diplomski.data.entites.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    UserDto findById(Long id);

    UserDto findByEmail(String email);

    UserDto createUser(CreateUserRecord createUserRecord);

    UserDto updateUser(UserDto userDto);

    Integer deleteUserByEmail(String email);


    List<UserDto> findAll();

    List<UserDto> findPotentialFriendUsers();

    List<UserDto> recommendFriends(String email);
}
