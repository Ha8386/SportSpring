package com.example.SportSpring.controller.user;

import com.example.SportSpring.entity.CommentEntity;
import com.example.SportSpring.service.CommentService;
import com.example.SportSpring.service.GetUserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private GetUserAuthentication getUserAuthentication;

    @PostMapping("/user/comment/{productId}")
    public String comment(@ModelAttribute("newComment") CommentEntity newComment,
                          @PathVariable Long productId){

        newComment.setUser(getUserAuthentication.getUser());
        commentService.addComment(newComment, productId);
        
        return "redirect:/home/product/{productId}";
    }
}
