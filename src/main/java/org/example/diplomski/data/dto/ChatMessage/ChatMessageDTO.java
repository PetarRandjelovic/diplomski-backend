package org.example.diplomski.data.dto.ChatMessage;

public record ChatMessageDTO(Long senderId, Long receiverId, String content,String timestamp) {
}
