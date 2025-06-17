package org.example.diplomski.mapper;

import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.example.diplomski.data.dto.ChatMessage.ChatMessageDTO;
import org.example.diplomski.data.entites.ChatMessage;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-11T23:22:29+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class ChatMessageMapperImpl implements ChatMessageMapper {

    @Override
    public ChatMessageDTO toDto(ChatMessage chatMessage) {
        if ( chatMessage == null ) {
            return null;
        }

        Long senderId = null;
        Long receiverId = null;
        String content = null;
        String timestamp = null;

        senderId = chatMessage.getSenderId();
        receiverId = chatMessage.getReceiverId();
        content = chatMessage.getContent();
        if ( chatMessage.getTimestamp() != null ) {
            timestamp = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( chatMessage.getTimestamp() );
        }

        ChatMessageDTO chatMessageDTO = new ChatMessageDTO( senderId, receiverId, content, timestamp );

        return chatMessageDTO;
    }
}
