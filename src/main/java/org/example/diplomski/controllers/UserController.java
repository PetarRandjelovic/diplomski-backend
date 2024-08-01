package org.example.diplomski.controllers;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.diplomski.data.dto.UserDto;
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

    @GetMapping(path = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/create/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        try {
            UserDto newUserDto = userService.createUser(userDto);
            return ResponseEntity.ok(newUserDto.getId());
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
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Transactional
    public ResponseEntity<?> deleteUserByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(userService.deleteUserByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

}
