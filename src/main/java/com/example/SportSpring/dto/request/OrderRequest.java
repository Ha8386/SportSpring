package com.example.SportSpring.dto.request;

import com.example.SportSpring.entity.OrderDetailEntity;
import com.example.SportSpring.entity.UserEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    Long id;
    Date orderDate;
    Long total;
    UserEntity user;
    List<OrderDetailEntity> orderDetails;
}