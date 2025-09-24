package com.example.SportSpring.dto.response;

import lombok.*;

@Data @AllArgsConstructor
public class ChatResponse {
    private Long conversationId;
    private String answer;
    private boolean handoff;
}
