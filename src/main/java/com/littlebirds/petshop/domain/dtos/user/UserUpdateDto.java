package com.littlebirds.petshop.domain.dtos.user;

import com.littlebirds.petshop.domain.dtos.address.AddressRegisterDto;

public record UserUpdateDto(String fullName, String phone, AddressRegisterDto addressRegisterDto) {
}
