package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.ChatMessage.GroupMessageDTO;
import org.example.diplomski.data.entites.GroupMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMessageMapper {


    GroupMessageDTO toDto(GroupMessage groupMessage);

}
