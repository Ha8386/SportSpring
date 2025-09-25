// src/main/java/com/example/SportSpring/dto/response/OrderStatusDTO.java
package com.example.SportSpring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data @AllArgsConstructor
public class OrderStatus {
    private Long id;
    private String code;
    private String status;
    private long total;
    private Date createdAt;
    private String detailUrl;
}
