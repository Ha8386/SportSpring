package com.example.SportSpring.dto.request;

import lombok.Data;

@Data
public class ChatRequest {
    private Long conversationId; 
    private String message;
}
