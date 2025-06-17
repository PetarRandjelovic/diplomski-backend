package org.example.diplomski.mapper;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.example.diplomski.data.dto.ChatMessage.GroupMessageDTO;
import org.example.diplomski.data.entites.GroupMessage;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-11T23:22:29+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class GroupMessageMapperImpl implements GroupMessageMapper {

    @Override
    public GroupMessageDTO toDto(GroupMessage groupMessage) {
        if ( groupMessage == null ) {
            return null;
        }

        Long groupId = null;
        Long senderId = null;
        String content = null;
        LocalDateTime timestamp = null;

        groupId = groupMessage.getGroupId();
        senderId = groupMessage.getSenderId();
        content = groupMessage.getContent();
        timestamp = groupMessage.getTimestamp();

        GroupMessageDTO groupMessageDTO = new GroupMessageDTO( groupId, senderId, content, timestamp );

        return groupMessageDTO;
    }
}
