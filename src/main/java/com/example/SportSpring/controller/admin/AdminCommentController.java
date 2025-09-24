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
        // Lấy danh sách comment dưới dạng DTO cho admin
        List<AdminCommentRow> rows = service.listForAdmin();

        // Đưa danh sách này vào model để render ra view
        model.addAttribute("comments", rows);

        // Trả về tên view: "templates/admin/comment/comment.html"
        return "admin/comment/comment";
    }

    /**
     * Hàm này cho phép admin xóa comment theo id
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        // Gọi service để xóa comment
        service.delete(id);

        return "redirect:/admin/comment?deleted=" + id;
    }

    /**
     * Hàm này cho phép admin phản hồi  comment của user
     */
    @PostMapping("/{id}/reply")
    public String reply(@PathVariable Long id,
                        @RequestParam("replyText") String replyText) {
        service.reply(id, replyText);

        return "redirect:/admin/comment?replied=" + id;
    }
}
