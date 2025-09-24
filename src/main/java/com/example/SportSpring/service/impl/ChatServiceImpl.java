package com.example.SportSpring.service.impl;

import com.example.SportSpring.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    @Value("${ai.provider:openai}")
    private String provider;

    @Value("${ai.model:gpt-4o-mini}")
    private String model;

    // KHÔNG commit key; lấy từ env: OPENAI_API_KEY
    @Value("${ai.api.key:#{null}}")
    private String apiKeyFromProps; // optional

    private final ObjectMapper om = new ObjectMapper();
    private final RestTemplate rest = new RestTemplate();

    @Override
    public String chatWithAi(List<String> history, String userMessage) {
        String key = Optional.ofNullable(apiKeyFromProps)
                .orElse(System.getenv("OPENAI_API_KEY"));
        if (key == null || key.isBlank()) {
            return "Thiếu OPENAI_API_KEY. Hãy cấu hình biến môi trường trên server.";
        }

        // build messages
        List<Map<String, Object>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system",
                "content", "Bạn là trợ lý SportShop. Trả lời lịch sự, ngắn gọn, tiếng Việt. "
                        + "Nếu cần, đề xuất sản phẩm theo nhu cầu chung. Không bịa chính sách."));
        for (String h : history) {
            messages.add(Map.of("role", "user", "content", h));
            messages.add(Map.of("role", "assistant", "content", "(đã trả lời trước đó)")); // placeholder nhẹ
        }
        messages.add(Map.of("role", "user", "content", userMessage));

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("messages", messages);
        body.put("temperature", 0.4);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(key);

        HttpEntity<Map<String, Object>> req = new HttpEntity<>(body, headers);

        String url = "https://api.openai.com/v1/chat/completions";
        Map resp = rest.postForObject(url, req, Map.class);
        try {
            List choices = (List) resp.get("choices");
            Map first = (Map) choices.get(0);
            Map msg = (Map) first.get("message");
            return String.valueOf(msg.get("content"));
        } catch (Exception e) {
            return "Xin lỗi, hệ thống tạm bận. Vui lòng thử lại.";
        }
    }
}
