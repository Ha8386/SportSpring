package com.example.SportSpring.configuration;

import com.example.SportSpring.service.impl.CustomOAuth2UserService;
import com.example.SportSpring.service.impl.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] PUBLIC_URLS = {
            "/css/**", "/js/**", "/image/**", "/home/**",
            "/login", "/register", "/error",
            "/oauth2/**", "/login/oauth2/**", "/oauth2/authorization/**",
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**"
    };

    private final UserDetailServiceImpl userDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final RoleBasedLoginSuccessHandler roleBasedLoginSuccessHandler;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    /**
     * (0) CHAT chain – dùng SESSION (IF_REQUIRED) để đọc phiên đăng nhập formLogin/OAuth2.
     * Áp dụng CHỈ cho /api/chat/** và đứng trước các chain khác.
     */
    @Bean
    @Order(0)
    public SecurityFilterChain chatSecurity(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/chat/**")
                // Nếu bạn chỉ GET thì có thể bật CSRF; ở đây disable cho đơn giản với fetch
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/chat/best-sellers", "/api/chat/order-status").permitAll()
                        .requestMatchers("/api/chat/my-orders").authenticated()
                        .anyRequest().authenticated()
                )
                // cần bật formLogin để chain này hiểu session đăng nhập
                .formLogin(Customizer.withDefaults())
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(u -> u.userService(customOAuth2UserService))
                        .successHandler(roleBasedLoginSuccessHandler)
                );
        return http.build();
    }

    /**
     * (1) API chain – Stateless (ví dụ JWT) cho /api/** còn lại.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // có/không có 2 dòng dưới đều được; chain 0 đã xử lý /api/chat/**
                        .requestMatchers("/api/chat/best-sellers", "/api/chat/order-status").permitAll()
                        .requestMatchers("/api/chat/my-orders").authenticated()
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        return http.build();
    }

    /**
     * (2) WEB chain – cho trang Thymeleaf (stateful).
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webSecurity(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(roleBasedLoginSuccessHandler)
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler(roleBasedLoginSuccessHandler)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .userDetailsService(userDetailsService);

        return http.build();
    }
}
