package org.example.diplomski.mapper;


import org.example.diplomski.data.dto.ChatMessage.ChatMessageDTO;
import org.example.diplomski.data.entites.ChatMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {

    ChatMessageDTO toDto(ChatMessage chatMessage);
}
