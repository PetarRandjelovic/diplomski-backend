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
        userRelationshipDto.setUserId(userRelationship.getUser1().getId());
        userRelationshipDto.setEmail(userRelationship.getUser1().getEmail());
        userRelationshipDto.setFollowedUserId(userRelationship.getUser2().getId());
        userRelationshipDto.setFollowedEmail(userRelationship.getUser2().getEmail());

        return userRelationshipDto;
    }
    

}
