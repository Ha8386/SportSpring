package com.example.SportSpring.controller.web;

import com.example.SportSpring.entity.CommentEntity;
import com.example.SportSpring.entity.UserEntity;
import com.example.SportSpring.enums.StatusEnum;
import com.example.SportSpring.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class WebController {

    private final ProductService productService;
    private final BrandService brandService;
    private final CartService cartService;
    private final GetUserAuthentication getUserAuthentication;
    private final CommentService commentService;

    @GetMapping
    public ModelAndView homePage() {
        ModelAndView mav = new ModelAndView("web/home");

        mav.addObject("brands", brandService.getAllPaginated(0, 8, "id", "asc", null, StatusEnum.Active));
        mav.addObject("products", productService.getAllProductsPaginated(0, 10, "id", "asc", null, StatusEnum.Active));
        mav.addObject("productSales", productService.getProductSale());
        mav.addObject("productDates", productService.getProductNewest());
        mav.addObject("quantityProduct", productService.getProductNewest().size());

        UserEntity user = getUserAuthentication.getUser();
        if (user != null) {
            mav.addObject("user", user);
            mav.addObject("quantity", cartService.getCountByUserId(user.getId()));
        }

        return mav;
    }

    @GetMapping("/contact")
    public ModelAndView getContact() {
        return new ModelAndView("web/contact");
    }

    @GetMapping("/introduce")
    public ModelAndView getIntroduce() {
        return new ModelAndView("web/introduce");
    }

    @GetMapping("/product/{productId}")
    public ModelAndView getProductDetail(@PathVariable Long productId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size) {

        ModelAndView mav = new ModelAndView("web/product_detail");

        // Sản phẩm
        mav.addObject("product", productService.getProductById(productId));

        // Phân trang comment (mới nhất trước)
        Page<CommentEntity> commentsPage = commentService.findByProductId(
                productId,
                PageRequest.of(Math.max(page, 0), Math.max(size, 1), Sort.by(Sort.Direction.DESC, "createDate"))
        );

        List<CommentEntity> comments = commentsPage.getContent();
        mav.addObject("commentsPage", commentsPage);
        mav.addObject("comments", comments);                 // để phần lặp cũ dùng được
        mav.addObject("newComment", new CommentEntity());    // nếu cần bind form ở nơi khác

        // Thống kê rating (tính trên toàn bộ đánh giá của sản phẩm)
        Double avgRating = commentService.getAverageRatingByProductId(productId);
        Long totalReviews = commentService.countByProductId(productId);
        Map<Integer, Long> starCounts = commentService.countStarsByProductId(productId);

        // Null-safe defaults
        mav.addObject("avgRating", avgRating != null ? avgRating : 0.0);
        mav.addObject("totalReviews", totalReviews != null ? totalReviews : 0L);
        mav.addObject("starCounts", starCounts); // template đã null-safe, cho phép null

        // User & giỏ hàng
        UserEntity user = getUserAuthentication.getUser();
        if (user != null) {
            mav.addObject("user", user);
            mav.addObject("count", cartService.getCountByUserId(user.getId())); // header đang dùng 'count'
        }

        return mav;
    }
}
