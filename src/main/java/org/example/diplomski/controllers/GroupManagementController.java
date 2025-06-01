package org.example.diplomski.controllers;

import org.example.diplomski.data.dto.ChatMessage.ChatGroupCreateDTO;
import org.example.diplomski.data.dto.ChatMessage.UserGroupsDto;
import org.example.diplomski.data.entites.ChatGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/groups-management")
public class GroupManagementController {

    private final org.example.diplomski.services.impl.GroupManagementService groupManagementService;

    public GroupManagementController(org.example.diplomski.services.impl.GroupManagementService groupManagementService) {
        this.groupManagementService = groupManagementService;
    }

    @PostMapping("/create")
    public ResponseEntity<ChatGroup> createGroup(@RequestBody ChatGroupCreateDTO dto) {
        return ResponseEntity.ok(groupManagementService.createGroup(dto));
    }

    @GetMapping("/my")
    public List<UserGroupsDto> getMyGroups(@RequestParam Long userId) {
        return groupManagementService.getGroupsForUser(userId);
    }
}
