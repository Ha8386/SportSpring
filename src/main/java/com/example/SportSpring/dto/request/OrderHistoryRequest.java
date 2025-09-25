package com.example.SportSpring.dto.request;

import com.example.SportSpring.enums.StatusOrderEnum;

public class OrderHistoryRequest {
    private StatusOrderEnum status = StatusOrderEnum.Dang_Xu_Ly;

    public OrderHistoryRequest() {}
    public OrderHistoryRequest(StatusOrderEnum status) { this.status = status; }

    public StatusOrderEnum getStatus() { return status; }
    public void setStatus(StatusOrderEnum status) { this.status = status; }
}
