package org.example.diplomski.services;


import org.example.diplomski.data.dto.CreateUserRelationshipRecord;
import org.example.diplomski.data.dto.UserDto;
import org.example.diplomski.data.dto.UserRelationshipAnswerRecord;
import org.example.diplomski.data.dto.UserRelationshipDto;
import org.example.diplomski.data.entites.UserRelationship;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserRelationshipService {

    Boolean followUnfollowUser(String senderEmail, String receiverEmail);
    Boolean unfollowUser(String senderEmail, String receiverEmail);
    List<UserRelationshipDto> getFollowedUsers(String senderEmail);
    List<UserRelationshipDto> getFollowingUsers(String senderEmail);
    int getFollowerCount(String senderEmail);
    int getFollowingCount(String senderEmail);
    UserRelationshipDto createFriendRequest(CreateUserRelationshipRecord createUserRelationshipRecord);

    UserRelationshipDto userRelationshipAnswer(UserRelationshipAnswerRecord userRelationshipAnswerRecord);
    int getUserFriendsCount(String email);


}
