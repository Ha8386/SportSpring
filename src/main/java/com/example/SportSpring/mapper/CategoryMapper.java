package com.example.SportSpring.mapper;

import com.example.SportSpring.dto.request.CategoryRequest;
import com.example.SportSpring.dto.response.CategoryResponse;
import com.example.SportSpring.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(CategoryEntity category);

    CategoryEntity toEntity(CategoryRequest categoryRequest);
}
