package com.example.SportSpring.configuration;

import com.example.SportSpring.entity.UserEntity;
import com.example.SportSpring.service.GetUserAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttribute {

    private final GetUserAuthentication getUserAuthentication;

    @ModelAttribute("user")
    public UserEntity putUserIntoModel() {
        return getUserAuthentication.getUser();
    }
}
