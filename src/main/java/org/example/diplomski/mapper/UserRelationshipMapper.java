package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.UserRelationshipDto;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.data.entites.UserRelationship;
import org.springframework.stereotype.Component;

@Component
public class UserRelationshipMapper {

    public UserRelationshipDto toDto (UserRelationship userRelationship) {
        UserRelationshipDto userRelationshipDto = new UserRelationshipDto();
        userRelationshipDto.setId(userRelationship.getId());
        userRelationshipDto.setUserId(userRelationship.getUser().getId());
        userRelationshipDto.setEmail(userRelationship.getUser().getEmail());
        userRelationshipDto.setFollowedUserId(userRelationship.getFollowedUser().getId());
        userRelationshipDto.setFollowedEmail(userRelationship.getFollowedUser().getEmail());

        return userRelationshipDto;
    }
    

}
