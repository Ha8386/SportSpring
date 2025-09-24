package com.example.SportSpring.mapper;

import com.example.SportSpring.dto.request.ProductRequest;
import com.example.SportSpring.dto.response.ProductResponse;
import com.example.SportSpring.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toProductResponse(ProductEntity productEntity);
    ProductEntity toProductEntity(ProductRequest productRequest);
    void updateProductEntity(@MappingTarget ProductEntity productEntity, ProductRequest productRequest);
}
