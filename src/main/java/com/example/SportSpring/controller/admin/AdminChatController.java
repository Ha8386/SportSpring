package com.example.SportSpring.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin")
public class AdminChatController {

    @GetMapping("/chat")
    public String chatPage() {
        // Tìm file ở /templates/admin/chat/chat.html
        return "admin/chat/chat";
    }
}
