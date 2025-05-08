package org.example.diplomski.services.impl;


import org.example.diplomski.data.dto.MessageDto;
import org.example.diplomski.data.entites.Message;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.mapper.MessageMapper;
import org.example.diplomski.repositories.MessageRepository;
import org.example.diplomski.repositories.UserRepository;
import org.example.diplomski.services.MessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository, MessageMapper messageMapper) {

        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public List<MessageDto> getMessagesBetweenUsers(String sender, String receiver) {
        User senderUser =userRepository.findByEmail(sender).get();
        User receiverUser =userRepository.findByEmail(receiver).get();

       List<Message> messages = messageRepository.findBySenderAndReceiver(senderUser, receiverUser);
        return messages.stream()
                .map(msg -> new MessageDto(msg.getSender().getEmail(), msg.getReceiver().getEmail(), msg.getContent(),msg.getTimestamp()))
                .collect(Collectors.toList());

    }

    @Override
    public Message saveMessage(MessageDto messageDto) {
     Message message = messageMapper.messageToMessage(messageDto);
        return messageRepository.save(message);
    }
}
