package com.example.SportSpring.controller.web;

import com.example.SportSpring.dto.request.ChatRequest;
import com.example.SportSpring.dto.response.ChatResponse;
import com.example.SportSpring.service.impl.AiOrchestratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final AiOrchestratorService orchestrator;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ChatResponse chat(@RequestBody ChatRequest req) {
        // TODO: nếu bạn có Spring Security user -> lấy userId hiện tại
        Long currentUserId = null; // hoặc userService.getCurrentUserId()
        return orchestrator.handle(req, currentUserId);
    }
}
