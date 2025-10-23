package com.littlebirds.petshop.domain.dtos.user;

import com.littlebirds.petshop.domain.enums.UserRole;
import com.littlebirds.petshop.domain.models.User;

import java.util.UUID;

public record AdminListDto(UUID id, String email, UserRole role) {

    public AdminListDto(User user) {
        this(user.getId(), user.getEmail(), user.getRole());
    }
}