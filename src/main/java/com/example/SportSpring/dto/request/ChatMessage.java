package com.example.SportSpring.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private Long conversationId;
    private String senderType;
    private String content;
}
