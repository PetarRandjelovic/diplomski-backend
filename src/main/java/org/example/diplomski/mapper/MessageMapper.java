package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.MessageDto;
import org.example.diplomski.data.entites.Message;
import org.example.diplomski.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    UserRepository userRepository;

  public Message messageToMessage(MessageDto messageDto) {
      Message message = new Message();
      message.setReceiver(userRepository.findByEmail(messageDto.getReceiverEmail()).get());
      message.setSender(userRepository.findByEmail(messageDto.getSenderEmail()).get());
      message.setContent(messageDto.getContent());

      return message;
  }

  public MessageDto messageToMessageDto(Message message) {
      MessageDto messageDto = new MessageDto();
      messageDto.setContent(message.getContent());
      messageDto.setSenderEmail(message.getSender().getEmail());
      messageDto.setReceiverEmail(message.getReceiver().getEmail());
      return messageDto;
  }
}
