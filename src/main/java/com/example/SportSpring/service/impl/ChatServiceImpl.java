package com.example.SportSpring.service.impl;

import com.example.SportSpring.dto.response.BestSellerProduct;
import com.example.SportSpring.dto.response.OrderStatus;
import com.example.SportSpring.entity.OrderEntity;
import com.example.SportSpring.entity.ProductEntity;
import com.example.SportSpring.repository.OrderRepository;
import com.example.SportSpring.repository.ProductRepository;
import com.example.SportSpring.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    @Value("${web.product.detail-pattern:/home/product/{id}}")
    private String productDetailPattern;

    @Value("${web.order.detail-pattern:/order/{id}}")
    private String orderDetailPattern;

    @Override
    public List<BestSellerProduct> getBestSellers(int limit) {
        var top = productRepo.findTopBestSellers(PageRequest.of(0, Math.max(1, limit)));
        return top.stream().map(this::mapToBestSellerDTO).toList();
    }

    private BestSellerProduct mapToBestSellerDTO(ProductEntity p) {
        String detailUrl = productDetailPattern.replace("{id}", String.valueOf(p.getId()));

        // Lấy ảnh đầu tiên nếu có
        String thumbnailUrl = null;
        if (p.getImages() != null && !p.getImages().isEmpty()) {
            thumbnailUrl = p.getImages().get(0).getImageLink();
        }

        return new BestSellerProduct(
                p.getId(),
                p.getName(),
                p.getPrice(),
                thumbnailUrl,          // giờ có ảnh thật nếu có
                p.getQuantitySell() == null ? 0L : p.getQuantitySell(),
                detailUrl
        );
    }


    @Override
    public List<OrderStatus> getMyOrders(Long currentUserId) {
        if (currentUserId == null) {
            throw new IllegalStateException("UNAUTHENTICATED");
        }
        var orders = orderRepo.findByUser_IdOrderByDateDesc(currentUserId);
        return orders.stream().map(o ->
                new OrderStatus(
                        o.getId(),
                        "#" + o.getId(),
                        o.getStatus() == null ? "UNKNOWN" : o.getStatus().name(),
                        o.getTotal() == null ? 0L : o.getTotal(),
                        o.getDate(),
                        orderDetailPattern.replace("{id}", String.valueOf(o.getId()))
                )
        ).toList();
    }


}
