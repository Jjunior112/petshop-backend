package com.littlebirds.petshop.domain.dtos.pet;

import com.littlebirds.petshop.domain.enums.PetType;

import com.littlebirds.petshop.domain.models.Pet;

import java.time.LocalDate;


public record PetListDto(Long id, String name, PetType petType, String race, String color, LocalDate born) {
    public PetListDto(Pet pet)
    {
        this(pet.getId(),pet.getName(),pet.getPetType(),pet.getRace(),pet.getColor(),pet.getBorn());
    }
}
