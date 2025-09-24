package com.example.SportSpring.service;

import com.example.SportSpring.dto.request.CancelOrderRequest;
import com.example.SportSpring.dto.request.OrderHistoryRequest;
import com.example.SportSpring.dto.response.OrderItemRowResponse;

import java.util.List;

public interface OrderHistoryService {
    List<OrderItemRowResponse> historyByStatus(Long userId, OrderHistoryRequest request);

    /** User yêu cầu hủy  */
    void requestCancel(Long userId, CancelOrderRequest request);

}
