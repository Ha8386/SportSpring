package com.example.SportSpring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity @Table(name = "messages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MessageEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "conversation_id")
    private ConversationEntity conversation;

    @Column(nullable = false)
    private String senderType; // USER | AI | ADMIN

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // theo dõi chi phí
    private Integer tokenIn;
    private Integer tokenOut;
    private Double costEstimate;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist void prePersist() { createdAt = Instant.now(); }
}
