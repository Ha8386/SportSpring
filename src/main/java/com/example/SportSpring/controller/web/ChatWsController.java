package com.example.SportSpring.controller.web;

import com.example.SportSpring.dto.request.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWsController {

    private final SimpMessagingTemplate messagingTemplate;

    // Client gửi /app/sendMessage
    @MessageMapping("/sendMessage")
    public void sendMessage(ChatMessage msg) {
        // phát lại cho tất cả client subscribe /topic/conversation.{id}
        messagingTemplate.convertAndSend("/topic/conversation." + msg.getConversationId(), msg);
    }
}
