package org.example.diplomski.data.dto.ChatMessage;

import java.util.List;

public record ChatGroupCreateDTO(String name, List<Long> memberIds) {
}