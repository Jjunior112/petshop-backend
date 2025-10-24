package com.littlebirds.petshop.application.controllers;

import com.littlebirds.petshop.application.services.PetService;
import com.littlebirds.petshop.domain.dtos.pet.PetListDto;
import com.littlebirds.petshop.domain.dtos.pet.PetRegisterDto;
import com.littlebirds.petshop.domain.dtos.user.AdminListDto;
import com.littlebirds.petshop.domain.dtos.user.AdminRegisterDto;
import com.littlebirds.petshop.domain.models.Pet;
import com.littlebirds.petshop.domain.models.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<PetListDto> petRegister(@RequestBody @Valid PetRegisterDto pet) {

        Pet response = petService.createPet(pet);

        return ResponseEntity.ok().body(new PetListDto(response));
    }

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<List<PetListDto>> getAllPets()
    {

        var pets =  petService.findAllPets();

        return ResponseEntity.ok().body(pets);
    }

}
