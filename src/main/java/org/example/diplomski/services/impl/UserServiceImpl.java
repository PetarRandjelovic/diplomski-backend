package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.UserDto;
import org.example.diplomski.data.entites.Role;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.data.enums.RoleType;
import org.example.diplomski.exceptions.MissingRoleException;
import org.example.diplomski.mapper.UserMapper;
import org.example.diplomski.repositories.RoleRepository;
import org.example.diplomski.repositories.UserRepository;
import org.example.diplomski.services.UserService;
import org.example.diplomski.utils.SpringSecurityUtil;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Random;

@Service
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;



    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " not found."));
        System.out.println("??????hahahahahah");
        return userMapper.toDto(user);
    }

    @Override
    public UserDto findByEmail(String email) {
        System.out.println("Email: " + email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email: " + email + " not found."));
        return userMapper.toDto(user);
    }

    // @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        Role role = roleRepository.findByRoleType(RoleType.USER)
                .orElseThrow(() -> new MissingRoleException("USER"));

        user.setRole(role);
        user.setUsername(userDto.getEmail());
     //   user.setPassword(passwordEncoder.encode(Thread.currentThread().getName() + new Random().nextLong() + Thread.activeCount()));
        //    user.setPassword(passwordEncoder.encode("sifra"));


        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new NotFoundException("User with id: " + userDto.getId() + " not found."));

        user.setDateOfBirth(userDto.getDateOfBirth());


        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public Integer deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email: " + email + " not found."));
        if (SpringSecurityUtil.hasRoleRole("ROLE_ADMIN")) {
            return userRepository.removeUserByEmail(email);
        }
        System.out.println(SpringSecurityUtil.hasRoleRole("ROLE_USER") || SpringSecurityUtil.hasRoleRole("ROLE_PRIVATE") || SpringSecurityUtil.hasRoleRole("ROLE_PUBLIC"));

        if (SpringSecurityUtil.hasRoleRole("ROLE_USER") || SpringSecurityUtil.hasRoleRole("ROLE_PRIVATE") || SpringSecurityUtil.hasRoleRole("ROLE_PUBLIC")) {
            if (SpringSecurityUtil.getPrincipalEmail().equals(email)) {
                return userRepository.removeUserByEmail(email);
            }
        }
        throw new RuntimeException("You don't have permission to delete this user.");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found."));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

}
