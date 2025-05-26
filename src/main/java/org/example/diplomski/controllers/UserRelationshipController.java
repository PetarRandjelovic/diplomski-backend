package org.example.diplomski.controllers;


import lombok.RequiredArgsConstructor;
import org.example.diplomski.data.dto.CreateUserRelationshipRecord;
import org.example.diplomski.data.dto.UserDto;
import org.example.diplomski.data.dto.UserRelationship.UserRelationshipAnswerRecord;
import org.example.diplomski.data.dto.UserRelationship.UserRelationshipDto;
import org.example.diplomski.data.dto.UserRelationship.UserRelationshipRecord;
import org.example.diplomski.data.enums.RelationshipStatus;
import org.example.diplomski.services.UserRelationshipService;
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

    @PostMapping(path = "/create-request", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> sendFriendRequest(@RequestBody CreateUserRelationshipRecord createUserRelationshipRecord) {

        return ResponseEntity.ok( userRelationshipService.createFriendRequest(createUserRelationshipRecord));
    }

    @PostMapping(path = "/confirm-decline", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> sendFriendRequest(@RequestBody UserRelationshipAnswerRecord userRelationshipAnswerRecord) {

        return ResponseEntity.ok( userRelationshipService.userRelationshipAnswer(userRelationshipAnswerRecord));
    }

    @GetMapping(path = "user-follower/{email}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getUserFollowers(@PathVariable String email) {
        return ResponseEntity.ok(userRelationshipService.getFollowerCount(email));
    }

    @GetMapping(path = "user-following/{email}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getUserFollowing(@PathVariable String email) {
        return ResponseEntity.ok(userRelationshipService.getFollowingCount(email));
    }


    @GetMapping(path = "user-friends/{email}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getUserFriends(@PathVariable String email) {
        return ResponseEntity.ok(userRelationshipService.getUserFriendsCount(email));
    }

    @GetMapping(path = "relationship-status/{email1}/{email2}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getRelationshipStatus(@PathVariable String email1, @PathVariable String email2) {
        return ResponseEntity.ok(userRelationshipService.getRelationshipStatus(email1, email2));
    }

    @GetMapping(path = "incoming/{email}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getIncomingRequests(@PathVariable String email) {
        return ResponseEntity.ok(userRelationshipService.getIncomingRequests(email));
    }


}
