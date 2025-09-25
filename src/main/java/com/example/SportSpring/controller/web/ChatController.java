// src/main/java/com/example/SportSpring/controller/web/ChatController.java
package com.example.SportSpring.controller.web;

import com.example.SportSpring.dto.response.BestSellerProduct;
import com.example.SportSpring.dto.response.OrderStatus;
import com.example.SportSpring.repository.UserRepository;
import com.example.SportSpring.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService service;
    private final UserRepository userRepository;

    @GetMapping("/best-sellers")
    public ResponseEntity<List<BestSellerProduct>> bestSellers(@RequestParam(defaultValue = "4") int limit) {
        return ResponseEntity.ok(service.getBestSellers(limit));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<?> myOrders(@AuthenticationPrincipal UserDetails principal) {
        if (principal == null) return ResponseEntity.status(401).body("Vui lòng đăng nhập để xem lịch sử đơn hàng.");
        var userOpt = userRepository.findByUsername(principal.getUsername());
        if (userOpt.isEmpty()) return ResponseEntity.status(401).body("Tài khoản không hợp lệ.");
        Long userId = userOpt.get().getId();
        List<OrderStatus> list = service.getMyOrders(userId);
        return ResponseEntity.ok(list);
    }
}
