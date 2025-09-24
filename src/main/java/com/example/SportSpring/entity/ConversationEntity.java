package com.example.SportSpring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity @Table(name = "conversations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConversationEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // null nếu khách ẩn danh
    private Long userId;

    @Column(nullable = false)
    private String channel; // "WEB"

    @Column(nullable = false)
    private String status;  // OPEN | ESCALATED | RESOLVED

    @Column(nullable = false)
    private String assigneeType; // AI | HUMAN

    private Long assigneeId; // admin id khi HUMAN

    private Double lastConfidence; // độ tự tin của AI (0..1)

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist void prePersist() {
        createdAt = Instant.now();
        updatedAt = createdAt;
        if (channel == null) channel = "WEB";
        if (status == null) status = "OPEN";
        if (assigneeType == null) assigneeType = "AI";
    }
    @PreUpdate void preUpdate() { updatedAt = Instant.now(); }
}
