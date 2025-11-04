package com.littlebirds.petshop.domain.dtos.user;

import com.littlebirds.petshop.domain.dtos.address.AddressListDto;
import com.littlebirds.petshop.domain.enums.UserRole;
import com.littlebirds.petshop.domain.models.User;

import java.util.UUID;

public record UserListDto(
        UUID id,
        String fullName,
        String email,
        UserRole role,
        boolean isActive,
        AddressListDto addressListDto
) {
    public UserListDto(User user) {
        this(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                (user.getAddress() != null) ? new AddressListDto(user.getAddress()) : null
        );
    }
}