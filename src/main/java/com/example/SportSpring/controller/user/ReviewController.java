package com.example.SportSpring.controller.user;

import com.example.SportSpring.service.CommentService;
import com.example.SportSpring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user/comments")
@RequiredArgsConstructor
public class ReviewController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping
    public String create(@AuthenticationPrincipal UserDetails principal,
                         @RequestParam Long productId,
                         @RequestParam String messages,
                         @RequestParam Integer rate,
                         @RequestParam(value = "media", required = false) MultipartFile[] media,
                         RedirectAttributes ra) {
        if (principal == null) return "redirect:/login";
        Long userId = userService.findIdByUsername(principal.getUsername());

        List<MultipartFile> files = (media == null ? List.of() : Arrays.asList(media));
        commentService.createUserComment(userId, productId, messages, rate, files);

        ra.addFlashAttribute("message", "Đã gửi đánh giá, cảm ơn bạn!");
        return "redirect:/user/history?status=Da_Giao";
    }
}
