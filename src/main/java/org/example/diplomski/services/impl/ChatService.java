package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.ChatMessage.ChatMessageDTO;
import org.example.diplomski.data.dto.MessageDto;
import org.example.diplomski.data.entites.ChatMessage;
import org.example.diplomski.data.entites.Message;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.mapper.ChatMessageMapper;
import org.example.diplomski.repositories.ChatMessageRepository;
import org.example.diplomski.repositories.UserRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {


    private final ChatMessageRepository chatMessageRepository;


    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;
    private final ChatMessageMapper chatMessageMapper;

    public ChatService(ChatMessageRepository chatMessageRepository, SimpMessagingTemplate messagingTemplate,
                       UserRepository userRepository,
                       ChatMessageMapper chatMessageMapper) {
        this.chatMessageRepository = chatMessageRepository;
        this.messagingTemplate = messagingTemplate;
        this.userRepository = userRepository;
        this.chatMessageMapper = chatMessageMapper;

    }

    public void saveAndSend(ChatMessageDTO messageDTO) {
        // Save to DB
        ChatMessage message = new ChatMessage();
        message.setSenderId(messageDTO.senderId());
        message.setReceiverId(messageDTO.receiverId());
        message.setContent(messageDTO.content());
        message.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(message);

        System.out.println("hmmm");

        User receiver = userRepository.findById(messageDTO.receiverId()).get();
        // Send to receiver
        messagingTemplate.convertAndSendToUser(
                receiver.getEmail(), "/queue/messages", messageDTO);
    }

    public List<ChatMessageDTO> getChatHistory(Long userId1, Long userId2) {
        List<ChatMessage> l = chatMessageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
                userId1, userId2, userId1, userId2
        );

        return l.stream().map(chatMessageMapper::toDto).toList();

    }

}