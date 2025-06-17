package org.example.diplomski.mapper;

import java.util.List;
import javax.annotation.processing.Generated;
import org.example.diplomski.data.dto.ChatMessage.UserGroupsDto;
import org.example.diplomski.data.entites.ChatGroup;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-11T23:22:29+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class GroupManagerMapperImpl implements GroupManagerMapper {

    @Override
    public UserGroupsDto toDto(ChatGroup chatGroup) {
        if ( chatGroup == null ) {
            return null;
        }

        List<Long> membersId = null;
        Long id = null;
        String name = null;

        membersId = GroupManagerMapper.mapUsersToIds( chatGroup.getMembers() );
        id = chatGroup.getId();
        name = chatGroup.getName();

        UserGroupsDto userGroupsDto = new UserGroupsDto( id, name, membersId );

        return userGroupsDto;
    }
}
