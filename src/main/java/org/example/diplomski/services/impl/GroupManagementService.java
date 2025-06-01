package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.ChatMessage.ChatGroupCreateDTO;
import org.example.diplomski.data.dto.ChatMessage.UserGroupsDto;
import org.example.diplomski.data.entites.ChatGroup;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.mapper.GroupManagerMapper;
import org.example.diplomski.repositories.ChatGroupRepository;
import org.example.diplomski.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupManagementService {

    private final ChatGroupRepository chatGroupRepository;
    private final UserRepository userRepository;
    private final GroupManagerMapper groupManagerMapper;

    public GroupManagementService(ChatGroupRepository chatGroupRepository,
                                  UserRepository userRepository
    , GroupManagerMapper groupManagerMapper) {
        this.chatGroupRepository = chatGroupRepository;
        this.userRepository = userRepository;
        this.groupManagerMapper = groupManagerMapper;
    }

    public ChatGroup createGroup(ChatGroupCreateDTO dto) {
        ChatGroup group = new ChatGroup();
        group.setName(dto.name());

        List<User> members = userRepository.findAllById(dto.memberIds());
        group.setMembers(members);

        return chatGroupRepository.save(group);

    }



    public List<UserGroupsDto> getGroupsForUser(Long userId) {
        List<ChatGroup> groups = chatGroupRepository.findByMembers_Id(userId);


        return groups.stream().map(groupManagerMapper::toDto).toList();
    }
}
