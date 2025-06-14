package org.example.diplomski.controllers;


import lombok.RequiredArgsConstructor;
import org.example.diplomski.data.dto.user.CreateUserRecord;
import org.example.diplomski.data.dto.user.UserDto;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.exceptions.EmailTakenException;
import org.example.diplomski.exceptions.MissingRoleException;
import org.example.diplomski.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/users",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/search/users", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> searchUsers(@RequestParam String query) {
        return ResponseEntity.ok(userService.searchUsers(query));
    }


    @GetMapping(path = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);

        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(userDto);
    }


    @GetMapping(path = "/all", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getAllUsers() {
        List<UserDto> list = userService.findAll();

        return ResponseEntity.ok(list);
    }

    @PostMapping("/create/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRecord userDto) {
        try {

            UserDto newUserDto = userService.createUser(userDto);
            return ResponseEntity.ok(newUserDto);
        } catch (EmailTakenException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (MissingRoleException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(path = "/email/{email}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        UserDto userDto = userService.findByEmail(email);

        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping(path = "/delete/{email}", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER','PRIVATE','PUBLIC')")
    @Transactional
    public ResponseEntity<?> deleteUserByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(userService.deleteUserByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userDto));
    }


    @GetMapping(path = "/friends/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getFriendUsers(@PathVariable Long id) {
        List<UserDto> list = userService.getFriendUsers(id);

        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "recommended/{email}", consumes = MediaType.ALL_VALUE)
    public List<UserDto> getRecommendations(@PathVariable String email) {
        return userService.recommendFriends(email);
    }
}
