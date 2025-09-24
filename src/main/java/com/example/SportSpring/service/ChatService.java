package com.example.SportSpring.service;

public interface ChatService {
    String chatWithAi(java.util.List<String> history, String userMessage);

    default double estimateConfidence(String aiAnswer) {
        // Heuristic đơn giản: nếu AI trả lời quá ngắn hoặc có "không rõ"
        if (aiAnswer == null) return 0.0;
        String a = aiAnswer.toLowerCase();
        if (a.contains("không rõ") || a.contains("không chắc") || a.length() < 12) return 0.45;
        return 0.8; // tạm thời
    }
}
