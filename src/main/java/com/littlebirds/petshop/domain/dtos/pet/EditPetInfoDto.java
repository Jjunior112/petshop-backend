package com.littlebirds.petshop.domain.dtos.pet;

import java.time.LocalDate;

public record EditPetInfoDto(String name, String race, String color, LocalDate born) {
}
