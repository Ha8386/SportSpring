package com.example.SportSpring.dto.response;


import com.example.SportSpring.entity.OrderEntity;
import com.example.SportSpring.entity.ProductEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    Long id;
    Long quantity;
    Long price;
    Long discount;
    Long total;
    OrderEntity order;
    ProductEntity product;
}
