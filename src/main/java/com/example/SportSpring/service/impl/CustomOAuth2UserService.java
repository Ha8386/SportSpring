package com.example.SportSpring.service.impl;

import com.example.SportSpring.entity.UserEntity;
import com.example.SportSpring.enums.RoleEnum;
import com.example.SportSpring.enums.StatusEnum;
import com.example.SportSpring.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User src = super.loadUser(userRequest);

        Map<String, Object> attrs = new HashMap<>(src.getAttributes());
        String email   = (String) attrs.get("email");
        String name    = (String) attrs.get("name");
        String picture = (String) attrs.get("picture");

        log.debug("[OAuth2] email={}, name={}, picture={}", email, name, picture);
        if (email == null) throw new OAuth2AuthenticationException("Google không trả về email");

        // Tìm hoặc tạo user ứng với email
        UserEntity user = userRepository.findByUsername(email)
                .map(u -> {
                    u.setAvatar(picture);
                    u.setUpdateDate(new Date());
                    return u;
                })
                .orElseGet(() -> UserEntity.builder()
                        .username(email)
                        .email(email)
                        .fullName(name)
                        .avatar(picture)
                        .roles(RoleEnum.USER)
                        .status(StatusEnum.Active)
                        .createDate(new Date())
                        .updateDate(new Date())
                        .build()
                );

        user = userRepository.save(user);

        RoleEnum roleEnum = user.getRoles();
        String roleName = "ROLE_" + (roleEnum != null ? roleEnum.name() : "USER");
        Collection<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(roleName));


        // Nhúng thêm để controller dễ lấy
        attrs.put("username", user.getUsername()); // = email
        attrs.put("userId", user.getId());

        // nameAttributeKey chọn "email" là ổn định
        return new DefaultOAuth2User(authorities, attrs, "email");
    }
}
