package com.example.SportSpring.mapper;

import com.example.SportSpring.dto.response.OrderResponse;
import com.example.SportSpring.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toOrderResponse(OrderEntity order);
}