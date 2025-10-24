package com.littlebirds.petshop.domain.dtos.pet;

import com.littlebirds.petshop.domain.enums.PetType;

import com.littlebirds.petshop.domain.models.Pet;

import java.util.UUID;

public record PetListDto(Long id, String name, PetType petType, String race) {
    public PetListDto(Pet pet)
    {
        this(pet.getId(),pet.getName(),pet.getPetType(),pet.getRace());
    }
}
