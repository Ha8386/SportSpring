package com.example.SportSpring.service.impl;

import com.example.SportSpring.dto.request.ChatRequest;
import com.example.SportSpring.dto.response.ChatResponse;
import com.example.SportSpring.entity.ConversationEntity;
import com.example.SportSpring.entity.MessageEntity;
import com.example.SportSpring.repository.ConversationRepository;
import com.example.SportSpring.repository.MessageRepository;
import com.example.SportSpring.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiOrchestratorService {

    private final ConversationRepository conversationRepo;
    private final MessageRepository messageRepo;
    private final ChatService chatService;

    @Transactional
    public ChatResponse handle(ChatRequest req, Long currentUserIdOrNull) {
        ConversationEntity conv = (req.getConversationId() == null)
                ? conversationRepo.save(ConversationEntity.builder()
                .userId(currentUserIdOrNull).build())
                : conversationRepo.findById(req.getConversationId()).orElseThrow();

        // TODO: có thể load 3–5 message gần nhất làm history. Ở MVP để trống:
        String aiAnswer = chatService.chatWithAi(List.of(), req.getMessage());

        double conf = chatService.estimateConfidence(aiAnswer);
        conv.setLastConfidence(conf);
        conversationRepo.save(conv);

        // lưu message user
        messageRepo.save(MessageEntity.builder()
                .conversation(conv)
                .senderType("USER")
                .content(req.getMessage())
                .build());

        // lưu message AI
        messageRepo.save(MessageEntity.builder()
                .conversation(conv)
                .senderType("AI")
                .content(aiAnswer)
                .build());

        boolean handoff = conf < 0.6;
        if (handoff) {
            conv.setStatus("ESCALATED");
            conv.setAssigneeType("HUMAN");
            conversationRepo.save(conv);
        }
        return new ChatResponse(conv.getId(), aiAnswer, handoff);
    }
}
