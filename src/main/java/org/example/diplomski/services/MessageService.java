package org.example.diplomski.services;


import org.example.diplomski.data.dto.MessageDto;
import org.example.diplomski.data.entites.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    public List<MessageDto> getMessagesBetweenUsers(String sender, String receiver);
    public Message saveMessage(MessageDto messageDto);
}
