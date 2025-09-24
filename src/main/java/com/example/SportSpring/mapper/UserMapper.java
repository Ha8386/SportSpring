package com.example.SportSpring.mapper;


import com.example.SportSpring.dto.request.CreateUserRequest;
import com.example.SportSpring.dto.request.UpdateUserRequest;
import com.example.SportSpring.dto.response.UserResponse;
import com.example.SportSpring.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(UserEntity userEntity);

    UserEntity toUserEntity(CreateUserRequest newUser);

    void updateUserEntity(@MappingTarget UserEntity userEntity, UpdateUserRequest updateUserRequest);
}
