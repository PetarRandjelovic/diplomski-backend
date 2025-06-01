package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.ChatMessage.ChatMessageDTO;
import org.example.diplomski.data.dto.ChatMessage.GroupMessageDTO;
import org.example.diplomski.data.entites.ChatMessage;
import org.example.diplomski.data.entites.GroupMessage;
import org.example.diplomski.mapper.GroupMessageMapper;
import org.example.diplomski.repositories.ChatMessageRepository;
import org.example.diplomski.repositories.GroupMessageRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupChatService {

    private final SimpMessagingTemplate messagingTemplate;
    private final GroupMessageRepository groupMessageRepository;
    private final GroupMessageMapper groupMessageMapper;

    public GroupChatService(SimpMessagingTemplate messagingTemplate,
                            GroupMessageRepository groupMessageRepository
    , GroupMessageMapper groupMessageMapper) {
        this.messagingTemplate = messagingTemplate;
        this.groupMessageRepository = groupMessageRepository;
        this.groupMessageMapper = groupMessageMapper;
    }

    public void saveAndBroadcast(GroupMessageDTO dto) {
        GroupMessage msg = new GroupMessage();
        msg.setGroupId(dto.groupId());
        msg.setSenderId(dto.senderId());
        msg.setContent(dto.content());
        msg.setTimestamp(LocalDateTime.now());
        groupMessageRepository.save(msg);

        messagingTemplate.convertAndSend(
                "/topic/group." + dto.groupId(), dto
        );
    }

    public List<GroupMessageDTO> getChatHistory(Long id) {
        List<GroupMessage> list=groupMessageRepository.findByGroupId(id);

        return list.stream().map(groupMessageMapper::toDto).collect(Collectors.toList());


    }

}