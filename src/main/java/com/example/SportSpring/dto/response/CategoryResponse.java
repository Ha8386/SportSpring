package com.example.SportSpring.dto.response;

import com.example.SportSpring.entity.ProductEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    Long id;
    String name;
    String image;
    List<ProductEntity> products;
}
