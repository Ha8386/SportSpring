package com.example.SportSpring.service;

import com.example.SportSpring.dto.response.DailyRevenue;
import com.example.SportSpring.dto.response.OrderResponse;
import com.example.SportSpring.entity.UserEntity;
import com.example.SportSpring.enums.StatusOrderEnum;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface OrderService {
    // View count Order
    Long getCount();

    Long getIncrease();

    // View all Order
    List<OrderResponse> getAllOrder();

    // Get Order By orderId;
    OrderResponse getOrderById(Long orderId);

    // AddOrder:
    // TẠO ORDER TỪ GIỎ của user (đổ OrderDetail từ Cart, tính total, và set quantity = TỔNG SỐ LƯỢNG)
    Long AddOrder(UserEntity user);

    // Update ToTal Price:
    // Tính lại total và quantity = TỔNG SỐ LƯỢNG từ OrderDetail
    void UpdateToTalPrice(Long orderId);

    // Get Order By UserId
    List<OrderResponse> historyBuy(Long userId);

    // Update Status Order
    void updateStatusOrder(Long orderId, StatusOrderEnum status);

    List<DailyRevenue> getRevenueByDay(LocalDate startDate, LocalDate endDate);

    void confirmPaidAndAdjustStock(Long orderId);

    /** Admin xác nhận hủy: cập nhật kho và set trạng thái Da_Huy */
    void confirmCancel(Long orderId);

    /** (Tùy chọn) Admin từ chối yêu cầu hủy -> trả về Dang_Xu_Ly */
    void rejectCancel(Long orderId);
}
