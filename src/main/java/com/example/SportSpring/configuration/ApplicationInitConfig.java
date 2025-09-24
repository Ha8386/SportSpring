package com.example.SportSpring.configuration;

import com.example.SportSpring.entity.BrandEntity;
import com.example.SportSpring.entity.CategoryEntity;
import com.example.SportSpring.entity.UserEntity;
import com.example.SportSpring.enums.RoleEnum;
import com.example.SportSpring.enums.StatusEnum;
import com.example.SportSpring.repository.BrandRepository;
import com.example.SportSpring.repository.CategoryRepository;
import com.example.SportSpring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ApplicationInitConfig {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, 
                                        BrandRepository brandRepository,
                                        CategoryRepository categoryRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                UserEntity user = UserEntity.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(RoleEnum.ADMIN)
                        .status(StatusEnum.Active)
                        .build();
                userRepository.save(user);
            }


            if(brandRepository.count() == 0){
                List<BrandEntity> brands = new ArrayList<>();

                brands.add(BrandEntity.builder()
                        .name("Nike")
                        .image("https://cdn.shopify.com/s/files/1/0456/5070/6581/files/ALLBRANDS_NIKE.jpg?v=1715164070")
                        .build());
                
                brands.add(BrandEntity.builder()
                        .name("Adidas")
                        .image("https://cdn.shopify.com/s/files/1/0456/5070/6581/files/ALLBRANDS_DAS.jpg?v=1715164070")
                        .build());

                brands.add(BrandEntity.builder()
                        .name("Hoka")
                        .image("https://cdn.shopify.com/s/files/1/0456/5070/6581/files/HOKA_30296b60-fa58-4266-87ef-6d6916626d47.jpg?v=1715164070")
                        .build());

                brands.add(BrandEntity.builder()
                        .name("Fila")
                        .image("https://cdn.shopify.com/s/files/1/0456/5070/6581/files/ALLBRANDS_FILA.jpg?v=1715164070")
                        .build());
        
                brandRepository.saveAll(brands);
            }

            if(categoryRepository.count() == 0){
                List<CategoryEntity> categories = new ArrayList<>();

                categories.add(new CategoryEntity("QUAN_AO"));
                categories.add(new CategoryEntity("GIAY_DEP"));
                categories.add(new CategoryEntity("BALO"));
                categories.add(new CategoryEntity("TUI"));
        
                categoryRepository.saveAll(categories);
            }
        };
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

