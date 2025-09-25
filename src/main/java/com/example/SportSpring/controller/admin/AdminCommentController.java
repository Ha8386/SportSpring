package com.example.SportSpring.controller.admin;

import com.example.SportSpring.dto.response.AdminCommentRow;
import com.example.SportSpring.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/comment")
public class AdminCommentController {


    private final CommentService service;


    public AdminCommentController(CommentService service) {
        this.service = service;
    }

    /**
     * Hàm này sẽ lấy danh sách comment để hiển thị cho admin
     */
    @GetMapping
    public String list(Model model) {
        List<AdminCommentRow> rows = service.listForAdmin();
        model.addAttribute("comments", rows);
        return "admin/comment/comment";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/comment?deleted=" + id;
    }

    @PostMapping("/{id}/reply")
    public String reply(@PathVariable Long id,
                        @RequestParam("replyText") String replyText) {
        service.reply(id, replyText);

        return "redirect:/admin/comment?replied=" + id;
    }
}
