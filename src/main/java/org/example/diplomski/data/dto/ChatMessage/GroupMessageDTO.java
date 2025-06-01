package org.example.diplomski.data.dto.ChatMessage;

import java.time.LocalDateTime;

public record GroupMessageDTO(Long groupId, Long senderId, String content, LocalDateTime timestamp) {}
