package com.example.SportSpring.dto.response;

import com.example.SportSpring.entity.OrderDetailEntity;
import com.example.SportSpring.entity.UserEntity;
import com.example.SportSpring.enums.StatusOrderEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;
    Date date;
    Long total;
    Long quantity;
    String phone;
    String address;
    StatusOrderEnum status;
    UserEntity user;
    List<OrderDetailEntity> items;
}
