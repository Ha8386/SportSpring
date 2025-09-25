package com.example.SportSpring.controller.admin;

import com.example.SportSpring.dto.response.OrderDetailResponse;
import com.example.SportSpring.dto.response.OrderResponse;
import com.example.SportSpring.enums.StatusOrderEnum;
import com.example.SportSpring.service.OrderDetailService;
import com.example.SportSpring.service.OrderService;
import com.example.SportSpring.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class OrderManagementController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private QRCodeService qrCodeService;

    @GetMapping()
    public ModelAndView orderManagement() {
        return new ModelAndView("/admin/order/management")
                .addObject("orders", orderService.getAllOrder()) // Lấy toàn bộ đơn hàng
                .addObject("status_orders", StatusOrderEnum.values()); // Lấy danh sách enum trạng thái đơn
    }


    @GetMapping("/{orderId}")
    public ModelAndView getOrderById(@PathVariable Long orderId) {
        OrderResponse order = orderService.getOrderById(orderId);
        List<OrderDetailResponse> items = orderDetailService.getItemByOrderId(orderId);
        StringBuilder qrContent = new StringBuilder("Đơn hàng #" + order.getId() + ":\n");
        for (OrderDetailResponse item : items) {
            qrContent.append("- ")
                    .append(item.getProduct().getName())
                    .append(" x").append(item.getQuantity())
                    .append(" - ").append(item.getProduct().getPrice())
                    .append("đ\n");
        }

        String qrCode = qrCodeService.generateQRCodeBase64(qrContent.toString(), 200, 200);
        return new ModelAndView("/admin/order/view")
                .addObject("order", order)
                .addObject("items", items)
                .addObject("qrCode", qrCode);
    }

    @GetMapping("/{orderId}/{status}")
    public String updateStatusOrder(@PathVariable("orderId") Long orderId,
                                    @PathVariable("status") StatusOrderEnum status) {
        orderService.updateStatusOrder(orderId, status);
        return "redirect:/admin/order";
    }

    @PostMapping("/{id}/confirm-cancel")
    public String confirmCancel(@PathVariable Long id, RedirectAttributes ra) {
        orderService.confirmCancel(id);
        ra.addFlashAttribute("message", "Đã hủy đơn #" + id + " và cập nhật tồn kho."); // Thông báo flash
        return "redirect:/admin/order";
    }
}
