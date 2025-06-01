package org.example.diplomski.services.impl;

import jakarta.transaction.Transactional;
import org.example.diplomski.data.dto.user.CreateUserRecord;
import org.example.diplomski.data.dto.user.UserDto;
import org.example.diplomski.data.entites.Role;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.data.entites.UserProfile;
import org.example.diplomski.data.entites.UserRelationship;
import org.example.diplomski.data.enums.RelationshipStatus;
import org.example.diplomski.data.enums.RoleType;
import org.example.diplomski.exceptions.EmailTakenException;
import org.example.diplomski.exceptions.MissingRoleException;
import org.example.diplomski.exceptions.UserIdNotFoundException;
import org.example.diplomski.mapper.UserMapper;
import org.example.diplomski.repositories.ImageDataRepository;
import org.example.diplomski.repositories.RoleRepository;
import org.example.diplomski.repositories.UserRelationshipRepository;
import org.example.diplomski.repositories.UserRepository;
import org.example.diplomski.services.UserRelationshipService;
import org.example.diplomski.services.UserService;
import org.example.diplomski.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageDataRepository imageDataRepository;
    private final UserRelationshipService userRelationshipService;
    private final UserRelationshipRepository userRelationshipRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, ImageDataRepository imageDataRepository,
                           UserRelationshipService userRelationshipService,
                           UserRelationshipRepository userRelationshipRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageDataRepository = imageDataRepository;
        this.userRelationshipService = userRelationshipService;
        this.userRelationshipRepository = userRelationshipRepository;
    }

    @Override
    public List<UserDto> searchUsers(String query) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }
        return userRepository.searchUsers(query.trim()).stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " not found."));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto findByEmail(String email) {
        System.out.println("Email: " + email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email: " + email + " not found."));
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public UserDto createUser(CreateUserRecord createUserRecord) {
        if (userRepository.findByEmail(createUserRecord.email()).isPresent()) {
            throw new EmailTakenException(createUserRecord.email());
        }

        User user = userMapper.toEntityFromRecord(createUserRecord);

        Role role = roleRepository.findByRoleType(RoleType.USER)
                .orElseThrow(() -> new MissingRoleException("USER"));

        user.setRole(role);
        user.setUsername(createUserRecord.password());
        user.setPassword(passwordEncoder.encode(createUserRecord.password()));

        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setCity("Unknown");
        profile.setInterests(new ArrayList<>());
        profile.setProfilePictureUrl(imageDataRepository.findByName("avatar.png").orElseThrow(() -> new NotFoundException("User profile image not found")));


        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new NotFoundException("User with id: " + userDto.getId() + " not found."));

        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setUsername(userDto.getUsername());


        System.out.println("Email: " + user.getEmail());
        System.out.println("Date: " + user.getDateOfBirth()
                + " Username: " + user.getUsername());


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
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto).toList();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + email + " not found."));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    @Override
    public List<UserDto> recommendFriends(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Get all current friends' emails
        Set<String> currentFriendEmails = userRelationshipRepository.findFriendEmails(email);

        // Add self to exclusion list
        currentFriendEmails.add(email);

        // Get the user's profile for city & interests
        UserProfile userProfile = user.getUserProfile();
        String city = userProfile.getCity();
        List<String> interests = userProfile.getInterests();

        // Fetch all users not already friends
        List<User> potentialUsers = userRepository.findAllExcludingEmails(currentFriendEmails);

        // Score users based on similarity
        return potentialUsers.stream()
                .sorted((u1, u2) -> {
                    int score1 = calculateScore(userProfile, u1.getUserProfile());
                    int score2 = calculateScore(userProfile, u2.getUserProfile());
                    return Integer.compare(score2, score1); // sort descending
                })
                .limit(10)
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getFriendUsers(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserIdNotFoundException(id.toString()));

        List<User> friends1 = userRelationshipRepository.findFriendsByUser1(id, RelationshipStatus.CONFIRMED);
        List<User> friends2 = userRelationshipRepository.findFriendsByUser2(id, RelationshipStatus.CONFIRMED);
        List<User> allFriends = Stream.concat(friends1.stream(), friends2.stream()).toList();

        System.out.println(allFriends);

        return allFriends.stream().map(userMapper::toDto).collect(Collectors.toList());
    }


    private int calculateScore(UserProfile base, UserProfile other) {
        int score = 0;
        if (base.getCity().equalsIgnoreCase(other.getCity())) score += 5;

        List<String> baseInterests = base.getInterests();
        List<String> otherInterests = other.getInterests();

        if (baseInterests != null && otherInterests != null) {
            long common = baseInterests.stream().filter(otherInterests::contains).count();
            score += (int) (common * 2);
        }

        return score;
    }
}
