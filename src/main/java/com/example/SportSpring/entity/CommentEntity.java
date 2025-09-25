package com.example.SportSpring.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "rate", nullable = false)
    Integer rate;

    @Column(name = "messages", nullable = false, length = 2000)
    String messages;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    Date createDate;

    @Column(name = "media_url", length = 255)
    String mediaUrl;

    @Column(name = "admin_reply", length = 2000)
    String adminReply;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reply_date")
    Date replyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;

    @PrePersist
    void prePersist() {
        if (createDate == null) {
            createDate = new Date();
        }
    }
}
