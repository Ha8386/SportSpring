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
        if (auth == null) return null;

        Object principal = auth.getPrincipal();

        // Đăng nhập bằng tài khoản thường
        if (principal instanceof UserDetailServiceImpl.CustomUserDetails custom) {
            return custom.getUser();
        }

        // Đăng nhập OAuth2/OIDC (Google/Facebook)
        if (principal instanceof OAuth2User oAuth2User) {
            String email = oAuth2User.getAttribute("email"); // Google/Facebook đều có
            if (email != null) {
                // bạn đã lưu username = email khi login OAuth
                return userRepository.findByUsername(email).orElse(null);
            }
        }
        // 3) Trường hợp anonymousUser
        if (principal instanceof String s && "anonymousUser".equals(s)) {
            return null;
        }
        return null;
    }
}
