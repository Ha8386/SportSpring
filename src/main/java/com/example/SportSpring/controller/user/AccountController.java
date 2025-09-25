package com.example.SportSpring.controller.user;

import com.example.SportSpring.dto.request.AddressRequest;
import com.example.SportSpring.dto.request.CreateUserRequest;
import com.example.SportSpring.dto.request.UpdateUserRequest;
import com.example.SportSpring.entity.UserEntity;
import com.example.SportSpring.enums.GenderEnum;
import com.example.SportSpring.service.OrderService;
import com.example.SportSpring.service.QRCodeService;
import com.example.SportSpring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;
    private final OrderService orderService;
    private final QRCodeService qrCodeService;

    /** Lấy key đăng nhập (email/username) an toàn cho cả Form & Google */
    private String resolveLoginKey(Object principal) {
        if (principal instanceof User ud) {
            return ud.getUsername();
        }
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

    @GetMapping("/info")
    public ModelAndView viewUser(@AuthenticationPrincipal Object principal,
                                 @RequestParam(required = false) Long id,
                                 RedirectAttributes ra) {
        String key = resolveLoginKey(principal);
        if (key == null) {
            ra.addFlashAttribute("error", "Vui lòng đăng nhập trước.");
            return new ModelAndView("redirect:/login");
        }
        UserEntity user = loadCurrentUserOrThrow(key);

        String qrLink = "http://localhost:8080/user/qr-info/" + user.getUsername();
        String qrCode = qrCodeService.generateQRCodeBase64(qrLink, 200, 200);

        return new ModelAndView("/user/information")
                .addObject("genders", GenderEnum.values())
                .addObject("updateUser", new CreateUserRequest())
                .addObject("newAddress", new AddressRequest())
                .addObject("qrCode", qrCode)
                .addObject("user", user);
    }

    @GetMapping("/qr-info/{username}")
    public ModelAndView viewUserInfoFromQR(@PathVariable String username) {
        UserEntity user = userService.findByUserName(username);
        return new ModelAndView("/user/qr-info").addObject("user", user);
    }

    @PostMapping("/update")
    public String updateUser(@AuthenticationPrincipal Object principal,
                             @ModelAttribute("updateUser") UpdateUserRequest request,
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes ra) {
        String key = resolveLoginKey(principal);
        if (key == null) {
            ra.addFlashAttribute("error", "Vui lòng đăng nhập.");
            return "redirect:/login";
        }
        UserEntity user = loadCurrentUserOrThrow(key);
        userService.updateUser(request, user.getId(), file);
        ra.addFlashAttribute("success", "Cập nhật thông tin thành công.");
        return "redirect:/user/info";
    }

    @PostMapping("/add-address")
    public String addAddress(@AuthenticationPrincipal Object principal,
                             @ModelAttribute("newAddress") AddressRequest request,
                             RedirectAttributes ra) {
        String key = resolveLoginKey(principal);
        if (key == null) {
            ra.addFlashAttribute("error", "Vui lòng đăng nhập.");
            return "redirect:/login";
        }
        UserEntity user = loadCurrentUserOrThrow(key);
        userService.addAddress(user.getId(), request);
        ra.addFlashAttribute("success", "Đã thêm địa chỉ.");
        return "redirect:/user/info";
    }

    @GetMapping("/delete-address/{addressId}")
    public String deleteAddress(@PathVariable Long addressId,
                                RedirectAttributes ra) {
        userService.deleteAddress(addressId);
        ra.addFlashAttribute("success", "Đã xoá địa chỉ.");
        return "redirect:/user/info";
    }

    @GetMapping("/checkout/{addressId}")
    public String checkout(@AuthenticationPrincipal Object principal,
                           @PathVariable Long addressId,
                           RedirectAttributes ra) {
        String key = resolveLoginKey(principal);
        if (key == null) {
            ra.addFlashAttribute("error", "Vui lòng đăng nhập.");
            return "redirect:/login";
        }
        UserEntity user = loadCurrentUserOrThrow(key);
        Long orderId = orderService.AddOrder(user);
        return "redirect:/payment?orderId=" + orderId + "&addressId=" + addressId;
    }
}
