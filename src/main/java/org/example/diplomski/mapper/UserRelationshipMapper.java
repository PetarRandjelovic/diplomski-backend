package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.UserRelationship.UserRelationshipDto;
import org.example.diplomski.data.dto.UserRelationship.UserRelationshipRecord;
import org.example.diplomski.data.entites.UserRelationship;
import org.example.diplomski.data.enums.RelationshipStatus;
import org.springframework.stereotype.Component;

@Component
public class UserRelationshipMapper {

    public UserRelationshipDto toDto (UserRelationship userRelationship) {
        UserRelationshipDto userRelationshipDto = new UserRelationshipDto();
        userRelationshipDto.setId(userRelationship.getId());
        userRelationshipDto.setUserId(userRelationship.getUser1().getId());
        userRelationshipDto.setEmail(userRelationship.getUser1().getEmail());
        userRelationshipDto.setFollowedUserId(userRelationship.getUser2().getId());
        userRelationshipDto.setFollowedEmail(userRelationship.getUser2().getEmail());
        userRelationshipDto.setStatus(userRelationship.getStatus());

        return userRelationshipDto;
    }

    public UserRelationshipRecord toUserRelationRecord(UserRelationship userRelationship) {

        return new UserRelationshipRecord(
                userRelationship.getUser1().getEmail(),userRelationship.getUser2().getEmail(), userRelationship.getStatus()
        );
    }


    public UserRelationshipRecord toUserRelationRecord(String email1, String email2, RelationshipStatus relationshipStatus) {
        return new UserRelationshipRecord(
                email1,email2, relationshipStatus
        );
    }
}
