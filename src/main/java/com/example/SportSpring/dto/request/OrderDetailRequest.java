package com.example.SportSpring.dto.request;

import com.example.SportSpring.entity.OrderEntity;
import com.example.SportSpring.entity.ProductEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequest {
    Long id;
    Long quantity;
    OrderEntity order;
    ProductEntity product;
}
