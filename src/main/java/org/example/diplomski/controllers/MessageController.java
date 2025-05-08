package org.example.diplomski.controllers;

import lombok.RequiredArgsConstructor;
import org.example.diplomski.data.dto.MessageDto;
import org.example.diplomski.data.entites.Message;
import org.example.diplomski.services.MessageService;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public MessageDto addUser(MessageDto message) {
        message.setContent(message.getSenderEmail() + " joined the chat");
        return message;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(MessageDto message) {
        System.out.println("Sending message: " + message);

        // Send to receiver's queue
        messagingTemplate.convertAndSendToUser(
                message.getReceiverEmail(), "/queue/messages", message
        );

        // Also send back to sender's queue (if they're not the same person)
        // This enables bidirectional communication
        if (!message.getSenderEmail().equals(message.getReceiverEmail())) {
            messagingTemplate.convertAndSendToUser(
                    message.getSenderEmail(), "/queue/messages", message
            );
        }
    }
}