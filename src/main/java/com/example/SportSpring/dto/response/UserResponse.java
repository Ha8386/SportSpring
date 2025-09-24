package com.example.SportSpring.dto.response;

import com.example.SportSpring.entity.AddressEntity;
import com.example.SportSpring.entity.CartEntity;
import com.example.SportSpring.enums.StatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String username;
    String password;
    String fullName;
    String gender;
    String phone;
    String email;
    String avatar;
    Date createDate;
    Date updateDate;
    String roles;
    StatusEnum status;
    List<CartEntity> carts;
    List<AddressEntity> addresses;
}
