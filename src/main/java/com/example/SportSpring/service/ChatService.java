package com.example.SportSpring.service;

import com.example.SportSpring.dto.response.BestSellerProduct;
import com.example.SportSpring.dto.response.OrderStatus;

import java.util.List;

public interface ChatService {
    List<BestSellerProduct> getBestSellers(int limit);
    List<OrderStatus> getMyOrders(Long currentUserId);

}