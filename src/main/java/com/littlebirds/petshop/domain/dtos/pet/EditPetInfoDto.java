package com.littlebirds.petshop.domain.dtos.pet;

import java.time.LocalDateTime;

public record EditPetInfoDto(String name, String race, String color, LocalDateTime born) {
}
