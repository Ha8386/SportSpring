package com.example.SportSpring.service.impl;

import com.example.SportSpring.entity.UserEntity;
import com.example.SportSpring.repository.UserRepository;
import com.example.SportSpring.service.GetUserAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserAuthenticationImpl implements GetUserAuthentication {

    private final UserRepository userRepository;

    @Override
    public UserEntity getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()
                || auth.getPrincipal() == null
                || "anonymousUser".equals(auth.getPrincipal())) {
            return null;
        }

        Object principal = auth.getPrincipal();

        // 1) Đăng nhập FORM: principal là UserDetails (User của Spring)
        if (principal instanceof org.springframework.security.core.userdetails.User ud) {
            // username ở đây CHÍNH LÀ email nếu bạn dùng email làm username
            return userRepository.findByUsername(ud.getUsername()).orElse(null);
        }

        // 2) Đăng nhập OAuth2: principal là OAuth2User
        if (principal instanceof org.springframework.security.oauth2.core.user.OAuth2User oAuth2User) {
            String email = oAuth2User.getAttribute("email");
            if (email != null) {
                return userRepository.findByUsername(email).orElse(null);
            }
            // fallback khác nếu cần (preferred_username, login, sub)...
        }

        return null;
    }
}
