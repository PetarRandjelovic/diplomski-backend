package org.example.diplomski.controllers;


import lombok.RequiredArgsConstructor;
import org.example.diplomski.data.dto.UserDto;
import org.example.diplomski.data.dto.UserRelationshipDto;
import org.example.diplomski.data.entites.UserRelationship;
import org.example.diplomski.services.UserRelationshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/relation",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class UserRelationshipController {

    private final UserRelationshipService userRelationshipService;


    @GetMapping(path = "/followed/{email}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> findFollowedUser(@PathVariable String email) {
        List<UserRelationshipDto> userRelationshipList = userRelationshipService.getFollowedUsers(email);

        return ResponseEntity.ok(userRelationshipList);
    }


    @GetMapping(path = "/following/{email}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> findFollowingUser(@PathVariable String email) {
        List<UserRelationshipDto> userRelationshipList = userRelationshipService.getFollowingUsers(email);

        return ResponseEntity.ok(userRelationshipList);
    }

    @GetMapping(path = "/follow-unfollow/{emailSender}/{email}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> followUnfollowUser(@PathVariable String emailSender, @PathVariable String email) {
      Boolean s= userRelationshipService.followUnfollowUser(emailSender, email);

        return ResponseEntity.ok(s);
    }

    @GetMapping(path = "user-follower/{email}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getUserFollowers(@PathVariable String email) {
        return ResponseEntity.ok(userRelationshipService.getFollowerCount(email));
    }

    @GetMapping(path = "user-following/{email}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getUserFollowing(@PathVariable String email) {
        return ResponseEntity.ok(userRelationshipService.getFollowingCount(email));
    }


}
