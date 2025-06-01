package org.example.diplomski.data.dto.ChatMessage;

import java.util.List;

public record UserGroupsDto(Long id, String name, List<Long> membersId) {
}
