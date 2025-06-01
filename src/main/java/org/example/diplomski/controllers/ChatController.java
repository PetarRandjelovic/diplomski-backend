package org.example.diplomski.controllers;

import org.example.diplomski.data.dto.ChatMessage.ChatMessageDTO;
import org.example.diplomski.data.entites.ChatMessage;
import org.example.diplomski.services.impl.ChatService;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(  value = "/api/chat")
public class ChatController {


    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("history")
    public List<ChatMessageDTO> getHistory(@RequestParam Long user1,
                                        @RequestParam Long user2) {
        return chatService.getChatHistory(user1, user2);
    }

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessageDTO messageDTO, Principal principal) {

        chatService.saveAndSend(messageDTO);
    }
}