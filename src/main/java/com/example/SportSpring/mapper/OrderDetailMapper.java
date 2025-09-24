package com.example.SportSpring.mapper;

import com.example.SportSpring.dto.response.OrderDetailResponse;
import com.example.SportSpring.entity.OrderDetailEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailResponse toOrderDetailResponse(OrderDetailEntity order);
}
