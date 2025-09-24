package com.example.SportSpring.controller.user;

import com.example.SportSpring.dto.response.CartItemResponse;
import com.example.SportSpring.dto.response.CartItemResponsePrint;
import com.example.SportSpring.entity.CartEntity;
import com.example.SportSpring.entity.UserEntity;
import com.example.SportSpring.service.CartService;
import com.example.SportSpring.service.OrderService;
import com.example.SportSpring.service.QRCodeService;
import com.example.SportSpring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("user/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;
    private final QRCodeService qrCodeService;

    private String resolveLoginKey(Object principal) {
        if (principal instanceof User ud) return ud.getUsername();
        if (principal instanceof OAuth2User ou) {
            String email = ou.getAttribute("email");
            if (email != null) return email;
            String username = ou.getAttribute("username");
            if (username != null) return username;
            return ou.getName();
        }
        return null;
    }

    private UserEntity loadCurrentUserOrThrow(String key) {
        return userService.findByUserName(key);
    }

    @GetMapping("/{userId}")
    public ModelAndView getCart(@AuthenticationPrincipal Object principal,
                                @PathVariable Long userId,
                                RedirectAttributes ra) {
        String key = resolveLoginKey(principal);
        if (key == null) {
            ra.addFlashAttribute("error", "Vui lòng đăng nhập.");
            return new ModelAndView("redirect:/login");
        }
        UserEntity user = loadCurrentUserOrThrow(key);

        // Bỏ qua userId trên URL, chỉ dùng id của user đang đăng nhập
        return new ModelAndView("/user/cart")
                .addObject("carts", cartService.getCart(user.getId()))
                .addObject("user", user);
    }

    @PostMapping("/{productId}")
    public ModelAndView addCart(@AuthenticationPrincipal Object principal,
                                @PathVariable Long productId,
                                Model model,
                                RedirectAttributes ra) {
        String key = resolveLoginKey(principal);
        if (key == null) {
            ra.addFlashAttribute("error", "Vui lòng đăng nhập.");
            return new ModelAndView("redirect:/login");
        }
        UserEntity user = loadCurrentUserOrThrow(key);

        ModelAndView mav = new ModelAndView("/user/cart").addObject("user", user);
        if (cartService.addCart(user.getId(), productId)) {
            model.addAttribute("notification", "Success");
            model.addAttribute("message", "Thêm Sản Phẩm Thành Công");
        } else {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", "Sản phẩm đã có trong giỏ");
        }
        mav.addObject("carts", cartService.getCart(user.getId()));
        return mav;
    }

    @GetMapping("/{productId}/{quantity}")
    public ModelAndView replaceQuantityProduct(@AuthenticationPrincipal Object principal,
                                               Model model,
                                               @PathVariable("productId") Long productId,
                                               @PathVariable("quantity") Long quantity,
                                               RedirectAttributes ra) {
        String key = resolveLoginKey(principal);
        if (key == null) {
            ra.addFlashAttribute("error", "Vui lòng đăng nhập.");
            return new ModelAndView("redirect:/login");
        }
        UserEntity user = loadCurrentUserOrThrow(key);

        ModelAndView mav = new ModelAndView("/user/cart")
                .addObject("carts", cartService.getCart(user.getId()))
                .addObject("user", user);

        if (cartService.replaceQuantityProduct(user.getId(), productId, quantity)) {
            model.addAttribute("notification", "Success");
            model.addAttribute("message", "Thêm thành công");
        } else {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", "Không đủ hàng");
        }
        return mav;
    }

    @GetMapping("/delete/{productId}")
    public String deleteCart(@AuthenticationPrincipal Object principal,
                             @PathVariable Long productId,
                             RedirectAttributes ra) {
        String key = resolveLoginKey(principal);
        if (key == null) {
            ra.addFlashAttribute("error", "Vui lòng đăng nhập.");
            return "redirect:/login";
        }
        UserEntity user = loadCurrentUserOrThrow(key);
        cartService.removeProductToCart(user.getId(), productId);
        return "redirect:/user/cart/" + user.getId();
    }

    @GetMapping("/print")
    @ResponseBody
    public List<CartItemResponsePrint> printCart(@AuthenticationPrincipal Object principal) {
        String key = resolveLoginKey(principal);
        UserEntity user = loadCurrentUserOrThrow(key);
        List<CartEntity> carts = cartService.getCart(user.getId());

        return carts.stream()
                .map(cart -> new CartItemResponsePrint(
                        cart.getProduct().getName(),
                        cart.getQuantity().intValue(),
                        cart.getProduct().getPrice().doubleValue(),
                        (cart.getProduct().getImages() != null && !cart.getProduct().getImages().isEmpty())
                                ? cart.getProduct().getImages().get(0).getImageLink()
                                : "/images/default.jpg",
                        cart.getProduct().getId()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/print-view")
    public ModelAndView printCartView(@AuthenticationPrincipal Object principal,
                                      RedirectAttributes ra) {
        String key = resolveLoginKey(principal);
        if (key == null) {
            ra.addFlashAttribute("error", "Vui lòng đăng nhập.");
            return new ModelAndView("redirect:/login");
        }
        UserEntity user = loadCurrentUserOrThrow(key);
        List<CartEntity> carts = cartService.getCart(user.getId());

        StringBuilder qrContent = new StringBuilder("Giỏ hàng:\n");
        for (CartEntity cart : carts) {
            qrContent.append("- ").append(cart.getProduct().getName())
                    .append(" x").append(cart.getQuantity())
                    .append(" - ").append(cart.getProduct().getPrice())
                    .append("đ\n");
        }
        String qrCode = qrCodeService.generateQRCodeBase64(qrContent.toString(), 200, 200);

        return new ModelAndView("/user/cart_print_modal")
                .addObject("cartItems", carts)
                .addObject("qrCode", qrCode);
    }

    @GetMapping("/qr")
    @ResponseBody
    public Map<String, String> getCartQRCode(@AuthenticationPrincipal Object principal) {
        String key = resolveLoginKey(principal);
        UserEntity user = loadCurrentUserOrThrow(key);

        String qrLink = "http://localhost:8080/user/cart/qr-view?uid=" + user.getId();
        String qrCodeBase64 = qrCodeService.generateQRCodeBase64(qrLink, 150, 150);

        Map<String, String> result = new HashMap<>();
        result.put("qrCode", qrCodeBase64);
        return result;
    }

    @GetMapping("/qr-view")
    public ModelAndView viewCartFromQR(@RequestParam("uid") Long userId) {
        List<CartEntity> carts = cartService.getCart(userId);

        List<CartItemResponse> cartItems = carts.stream().map(cart -> new CartItemResponse(
                cart.getProduct().getName(),
                cart.getQuantity().intValue(),
                cart.getProduct().getPrice(),
                (cart.getProduct().getImages() != null && !cart.getProduct().getImages().isEmpty())
                        ? cart.getProduct().getImages().get(0).getImageLink()
                        : "/images/default.jpg"
        )).collect(Collectors.toList());

        String qrLink = "http://localhost:8080/user/cart/qr-view?uid=" + userId;
        String qrCode = qrCodeService.generateQRCodeBase64(qrLink, 150, 150);

        double totalAmount = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        return new ModelAndView("user/cart_qr_view")
                .addObject("totalAmount", totalAmount)
                .addObject("cartItems", cartItems)
                .addObject("qrCode", qrCode);
    }
}
