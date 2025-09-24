package com.example.SportSpring.mapper;

import com.example.SportSpring.dto.request.BrandRequest;
import com.example.SportSpring.dto.response.BrandResponse;
import com.example.SportSpring.entity.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandResponse toBrandResponse(BrandEntity brandEntity);

    BrandEntity toEntity(BrandRequest brandRequest);
}
