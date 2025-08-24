package com.leumas.finance.admin.mapper;

import com.leumas.finance.admin.controller.request.UserRequest;
import com.leumas.finance.admin.controller.response.UserResponse;
import com.leumas.finance.admin.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static User toUser(UserRequest request) {
        return User
                .builder()
                .name(request.name())
                .email(request.email())
                .cpf(request.cpf())
                .password(request.password())
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
