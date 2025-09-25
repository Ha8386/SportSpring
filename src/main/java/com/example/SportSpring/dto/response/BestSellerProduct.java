// src/main/java/com/example/SportSpring/dto/response/BestSellerProductDTO.java
package com.example.SportSpring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class BestSellerProduct {
    private Long id;
    private String name;
    private long price;
    private String thumbnailUrl;
    private long quantitySell;
    private String detailUrl;
}
