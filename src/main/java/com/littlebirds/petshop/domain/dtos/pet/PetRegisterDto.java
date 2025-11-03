package com.littlebirds.petshop.domain.dtos.pet;

import com.littlebirds.petshop.domain.enums.PetType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PetRegisterDto(String name, PetType petType, String race, String color, LocalDate born) {
}
