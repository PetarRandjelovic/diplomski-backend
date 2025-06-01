package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.ChatMessage.UserGroupsDto;
import org.example.diplomski.data.entites.ChatGroup;
import org.example.diplomski.data.entites.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.provisioning.GroupManager;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GroupManagerMapper {


    @Mapping(source = "members", target = "membersId", qualifiedByName = "mapUsersToIds")
    UserGroupsDto toDto(ChatGroup chatGroup);

    @Named("mapUsersToIds")
    static List<Long> mapUsersToIds(List<User> users) {
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

}
