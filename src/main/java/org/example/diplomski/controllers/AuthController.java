package org.example.diplomski.controllers;


import org.example.diplomski.data.dto.LoginDto;
import org.example.diplomski.data.dto.TokenDto;
import org.example.diplomski.data.dto.UserDto;
import org.example.diplomski.jwtUtils.JwtUtil;
import org.example.diplomski.mapper.UserMapper;
import org.example.diplomski.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;
    private final JwtUtil jwtUtil;


    public AuthController(AuthenticationProvider authenticationProvider, UserService userService, JwtUtil jwtUtil) {
        this.authenticationProvider = authenticationProvider;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginRequest) {
           try {
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(new TokenDto(jwtUtil.generateToken(userService.findByEmail(loginRequest.getEmail()))));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
        try {
            // Check if email already exists
//            if (userService.existsByEmail(userDto.getEmail())) {
//                return ResponseEntity.badRequest().body("Email is already taken.");
//            }

            // Encrypt the password
        //    userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

            // Save the user
            UserDto createdUser = userService.createUser(userDto);
            return ResponseEntity.ok(createdUser.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during sign-up.");
        }
    }
}
