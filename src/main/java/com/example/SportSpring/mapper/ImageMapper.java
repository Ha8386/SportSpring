package com.example.SportSpring.mapper;

import com.example.SportSpring.dto.response.ImageResponse;
import com.example.SportSpring.entity.ImageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageResponse toImageResponse(ImageEntity imageEntity);
}
