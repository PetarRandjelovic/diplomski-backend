package org.example.diplomski.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {


    private String senderEmail;
    private String content;
    private String receiverEmail;
    private String timestamp;
}
