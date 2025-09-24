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

    /**
     * GET /admin/order
     * Trang quản lý đơn hàng: hiển thị tất cả đơn hàng + danh sách trạng thái có thể lọc
     */
    @GetMapping()
    public ModelAndView orderManagement() {
        return new ModelAndView("/admin/order/management")
                .addObject("orders", orderService.getAllOrder()) // Lấy toàn bộ đơn hàng
                .addObject("status_orders", StatusOrderEnum.values()); // Lấy danh sách enum trạng thái đơn
    }

    /**
     * GET /admin/order/{orderId}
     * Hiển thị chi tiết 1 đơn hàng theo id
     */
    @GetMapping("/{orderId}")
    public ModelAndView getOrderById(@PathVariable Long orderId) {
        // Lấy thông tin đơn hàng
        OrderResponse order = orderService.getOrderById(orderId);

        // Lấy danh sách sản phẩm trong đơn hàng
        List<OrderDetailResponse> items = orderDetailService.getItemByOrderId(orderId);

        // Tạo nội dung cho QR Code (danh sách sản phẩm trong đơn)
        StringBuilder qrContent = new StringBuilder("Đơn hàng #" + order.getId() + ":\n");
        for (OrderDetailResponse item : items) {
            qrContent.append("- ")
                    .append(item.getProduct().getName()) // Tên sản phẩm
                    .append(" x").append(item.getQuantity()) // Số lượng
                    .append(" - ").append(item.getProduct().getPrice()) // Giá
                    .append("đ\n");
        }

        String qrCode = qrCodeService.generateQRCodeBase64(qrContent.toString(), 200, 200);

        // Trả về view hiển thị chi tiết đơn hàng
        return new ModelAndView("/admin/order/view")
                .addObject("order", order) // Đơn hàng
                .addObject("items", items) // Danh sách sản phẩm
                .addObject("qrCode", qrCode); // QR Code hiển thị
    }

    /**
     * GET /admin/order/{orderId}/{status}
     * Cập nhật trạng thái đơn hàng (VD: đã giao, đang xử lý…)
     */
    @GetMapping("/{orderId}/{status}")
    public String updateStatusOrder(@PathVariable("orderId") Long orderId,
                                    @PathVariable("status") StatusOrderEnum status) {
        orderService.updateStatusOrder(orderId, status); // Gọi service cập nhật trạng thái
        return "redirect:/admin/order"; // Quay lại danh sách đơn
    }

    /**
     * POST /admin/order/{id}/confirm-cancel
     * Admin xác nhận hủy đơn (trả hàng, cập nhật tồn kho)
     */
    @PostMapping("/{id}/confirm-cancel")
    public String confirmCancel(@PathVariable Long id, RedirectAttributes ra) {
        orderService.confirmCancel(id); // Xử lý hủy đơn và cập nhật kho
        ra.addFlashAttribute("message", "Đã hủy đơn #" + id + " và cập nhật tồn kho."); // Thông báo flash
        return "redirect:/admin/order"; // Quay lại danh sách
    }
}
