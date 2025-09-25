package com.example.SportSpring.controller;

import com.example.SportSpring.entity.CartEntity;
import com.example.SportSpring.entity.OrderEntity;
import com.example.SportSpring.entity.UserEntity;
import com.example.SportSpring.enums.StatusOrderEnum;
import com.example.SportSpring.repository.OrderRepository;
import com.example.SportSpring.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Autowired private PaymentService paymentService;
    @Autowired private OrderService orderService;
    @Autowired private UserService userService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private CartService cartService;
    @Autowired private GetUserAuthentication getUserAuthentication;


    @GetMapping
    public String showPaymentPage(@RequestParam(value = "orderId", required = false) Long orderId,
                                  @RequestParam(value = "addressId", required = false) Long addressIdParam,
                                  @RequestParam(value = "addressid", required = false) Long addressidParam,
                                  @RequestParam(value = "subtotal", required = false) Long subtotalParam,
                                  Model model) {

        Long addressId = (addressIdParam != null) ? addressIdParam : addressidParam;

        if (orderId == null) {
            UserEntity user = getUserAuthentication.getUser();
            Long newOrderId = orderService.AddOrder(user);
            String url = UriComponentsBuilder.fromPath("/payment")
                    .queryParam("orderId", newOrderId)
                    .queryParam("addressId", addressId)
                    .queryParam("subtotal", subtotalParam)
                    .build().toUriString();
            return "redirect:" + url;
        }

        var orderDto = orderService.getOrderById(orderId);
        if (orderDto == null) {
            model.addAttribute("error", "Order not found: " + orderId);
            return "error";
        }

        Long subtotal = (subtotalParam != null) ? subtotalParam
                : (orderDto.getTotal() != null ? orderDto.getTotal() : 0L);

        model.addAttribute("order", orderDto);
        model.addAttribute("addressId", addressId);
        model.addAttribute("subtotal", subtotal);

        log.info("[PAYMENT][GET] orderId={} order.total={} subtotal={}",
                orderId, orderDto.getTotal(), subtotal);

        return "user/payment";
    }


    @PostMapping("/process")
    public String processPayment(@RequestParam("orderId") Long orderId,
                                 @RequestParam(value = "addressId", required = false) Long addressId,
                                 @RequestParam("paymentMethod") String paymentMethod,
                                 @RequestParam(value = "subtotal", required = false) Long subtotalParam,
                                 HttpServletRequest request,
                                 Model model) {
        try {
            var orderDto = orderService.getOrderById(orderId);
            if (orderDto == null) {
                model.addAttribute("error", "Order not found: " + orderId);
                return "error";
            }

            if (addressId != null) {
                userService.updateOrderAddress(orderId, addressId);
            }

            Long amount = (orderDto.getTotal() != null && orderDto.getTotal() > 0)
                    ? orderDto.getTotal()
                    : (subtotalParam != null && subtotalParam > 0 ? subtotalParam : null);

            log.info("[PAYMENT][PROCESS] orderId={} method={} order.total={} subtotal(form)={} -> amount={}",
                    orderId, paymentMethod, orderDto.getTotal(), subtotalParam, amount);

            if ("COD".equalsIgnoreCase(paymentMethod)) {
                persistTotalIfMissing(orderId, amount);
                orderService.confirmPaidAndAdjustStock(orderId);
                orderService.updateStatusOrder(orderId, StatusOrderEnum.Dang_Xu_Ly);
                clearCartOfOrderOwner(orderId);
                return "redirect:/user/history";
            }

            if ("VNPAY".equalsIgnoreCase(paymentMethod)) {
                if (amount == null || amount <= 0) {
                    model.addAttribute("error", "Invalid order amount");
                    return "error";
                }
                persistTotalIfMissing(orderId, amount);

                long amountVnd = BigDecimal.valueOf(amount)
                        .setScale(0, RoundingMode.HALF_UP)
                        .longValue();

                String orderInfo = "Thanh toan don hang " + orderId;
                String paymentUrl = paymentService.createPaymentUrl(orderId, amountVnd, orderInfo, request);
                log.info("[PAYMENT][PROCESS] Redirecting to VNPay: {}", paymentUrl);
                return "redirect:" + paymentUrl;
            }

            model.addAttribute("error", "Invalid payment method");
            return "error";

        } catch (Exception e) {
            log.error("[PAYMENT][PROCESS] Failed: {}", e.getMessage(), e);
            model.addAttribute("error", "Payment processing failed: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/vnpay-payment")
    public String vnpayPayment(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            String code = request.getParameter("vnp_ResponseCode");
            String status = request.getParameter("vnp_TransactionStatus");
            String txnRef = request.getParameter("vnp_TxnRef");
            String vnpAmount = request.getParameter("vnp_Amount");

            log.info("[VNPAY][CALLBACK] code={} status={} txnRef={} amount={}", code, status, txnRef, vnpAmount);

            // Người dùng hủy
            if ("24".equals(code)) {
                redirectAttributes.addFlashAttribute("message", "Thanh toán đã bị hủy");
                return "redirect:/user/history";
            }

            boolean isValid = paymentService.validatePaymentResponse(request);
            log.info("[VNPAY][CALLBACK] validate={}", isValid);

            if (isValid && "00".equals(code) && "00".equals(status)) {
                try {
                    Long orderId = Long.valueOf(txnRef);

                    Long amount = null;
                    try {
                        if (vnpAmount != null) amount = Long.parseLong(vnpAmount) / 100;
                    } catch (NumberFormatException ignore) {}

                    persistTotalIfMissing(orderId, amount);
                    orderService.confirmPaidAndAdjustStock(orderId);
                    orderService.updateStatusOrder(orderId, StatusOrderEnum.Dang_Xu_Ly);
                    clearCartOfOrderOwner(orderId);

                    try { paymentService.processPaymentCallback(request); } catch (Exception ex) {
                        log.warn("[VNPAY][CALLBACK] processPaymentCallback warn: {}", ex.getMessage());
                    }

                    redirectAttributes.addFlashAttribute("message", "Thanh toán thành công");
                    log.info("[VNPAY][CALLBACK] processed OK, orderId={}", orderId);
                } catch (Exception e) {
                    log.error("[VNPAY][CALLBACK] process error: {}", e.getMessage(), e);
                    redirectAttributes.addFlashAttribute("message", "Có lỗi xảy ra khi xử lý thanh toán");
                }
                return "redirect:/user/history";
            }

            redirectAttributes.addFlashAttribute("message", "Thanh toán thất bại");
            return "redirect:/user/history";

        } catch (Exception e) {
            log.error("[VNPAY][CALLBACK] exception: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("message", "Có lỗi xảy ra khi thanh toán");
            return "redirect:/user/history";
        }
    }

    // ================= Helpers =================

    private void persistTotalIfMissing(Long orderId, Long amount) {
        if (amount == null || amount <= 0) return;
        Optional<OrderEntity> op = orderRepository.findById(orderId);
        if (op.isPresent()) {
            OrderEntity oe = op.get();
            if (oe.getTotal() == null || oe.getTotal() <= 0) {
                oe.setTotal(amount);
                orderRepository.save(oe);
                log.info("[ORDER] set total={} for orderId={}", amount, orderId);
            }
        }
    }

    private void clearCartOfOrderOwner(Long orderId) {
        orderRepository.findById(orderId).ifPresent(oe -> {
            try {
                Long ownerId = oe.getUser() != null ? oe.getUser().getId() : null;
                if (ownerId != null) {
                    List<CartEntity> carts = cartService.getCart(ownerId);
                    if (carts != null) {
                        for (CartEntity c : carts) {
                            if (c.getProduct() != null) {
                                cartService.removeProductToCart(ownerId, c.getProduct().getId());
                            }
                        }
                        log.info("[CART] cleared cart of userId={} after orderId={}", ownerId, orderId);
                    }
                }
            } catch (Exception ex) {
                log.warn("[CART] cannot clear cart after orderId={}, err={}", orderId, ex.getMessage());
            }
        });
    }
}
