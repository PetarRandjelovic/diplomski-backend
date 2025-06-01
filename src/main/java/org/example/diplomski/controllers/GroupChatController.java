package org.example.diplomski.controllers;

import org.example.diplomski.data.dto.ChatMessage.GroupMessageDTO;
import org.example.diplomski.services.impl.GroupChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(  value = "/api/group-chat")
public class GroupChatController {

    private final GroupChatService groupChatService;

    public GroupChatController(GroupChatService groupChatService) {
        this.groupChatService = groupChatService;
    }


    @GetMapping("history/{groupId}")
    public List<GroupMessageDTO> getHistory(@PathVariable Long groupId) {
        return groupChatService.getChatHistory(groupId);
    }


    @MessageMapping("/group.send")
    public void sendGroupMessage(@Payload GroupMessageDTO messageDTO) {
        System.out.println(messageDTO);
        groupChatService.saveAndBroadcast(messageDTO);
    }
}
