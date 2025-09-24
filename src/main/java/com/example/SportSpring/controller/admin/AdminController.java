package com.example.SportSpring.controller.admin;

import com.example.SportSpring.service.OrderService;
import com.example.SportSpring.service.ProductService;
import com.example.SportSpring.service.RevenueService;
import com.example.SportSpring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;
    private final RevenueService revenueService;

    public AdminController(UserService userService,
                           ProductService productService,
                           OrderService orderService,
                           RevenueService revenueService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
        this.revenueService = revenueService;
    }

    // Map cả "" và "/" để tránh trượt handler
    @GetMapping({"", "/"})
    public ModelAndView homeAdmin() {
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        String increase = nf.format(orderService.getIncrease());

        LocalDate to = LocalDate.now();
        LocalDate from = to.minusDays(29);

        return new ModelAndView("admin/admin")  // KHÔNG có slash đầu
                .addObject("userQuantity", userService.countUsers())
                .addObject("productQuantity", productService.countProduct())
                .addObject("orderQuantity", orderService.getCount())
                .addObject("increase", increase)
                .addObject("revenues", orderService.getRevenueByDay(from, to))
                .addObject("productSales", revenueService.getTopProducts(from, to));
    }
}
