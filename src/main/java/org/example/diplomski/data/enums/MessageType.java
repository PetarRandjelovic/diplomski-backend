package org.example.diplomski.data.enums;

import lombok.Getter;

@Getter
public enum MessageType {

    CHAT("CHAT"),
    JOIN("JOIN"),
    LEAVE("LEAVE");


    private final String messageType;

    MessageType(String messageType) {
        this.messageType = messageType;
    }
}
