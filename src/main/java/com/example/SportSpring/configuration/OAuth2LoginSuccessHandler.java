package com.example.SportSpring.configuration;

import com.example.SportSpring.entity.UserEntity;
import com.example.SportSpring.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public OAuth2LoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2User oAuth2User) {
            String email = oAuth2User.getAttribute("email");

            if (email != null) {
                Optional<UserEntity> optUser = userRepository.findByUsername(email);
                optUser.ifPresent(user -> {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user); // để Thymeleaf dùng ${user}
                });
            }
        }

        // Redirect về trang chủ
        response.sendRedirect("/home");
    }
}
