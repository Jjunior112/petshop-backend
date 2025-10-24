package com.littlebirds.petshop.domain.dtos.pet;

import com.littlebirds.petshop.domain.enums.PetType;

public record PetRegisterDto(String name, PetType petType, String race) {
}
