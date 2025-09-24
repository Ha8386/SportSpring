package com.example.SportSpring.dto.response;

import com.example.SportSpring.entity.BrandEntity;
import com.example.SportSpring.entity.CategoryEntity;
import com.example.SportSpring.entity.ImageEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;
    String name;
    Long price;
    Long discount;
    String description;
    String color;
    Long quantity;
    Date createDate;
    Date updateDate;
    CategoryEntity category;
    BrandEntity brand;
    List<ImageEntity> images;
}
