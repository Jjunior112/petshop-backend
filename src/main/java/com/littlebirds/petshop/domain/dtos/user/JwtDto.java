package com.littlebirds.petshop.domain.dtos.user;

import java.util.UUID;

public record JwtDto(UUID userId, String token, String role) {
}
