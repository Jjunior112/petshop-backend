package com.littlebirds.petshop.application.controllers;

import com.littlebirds.petshop.application.services.PetService;
import com.littlebirds.petshop.domain.dtos.pet.EditPetInfoDto;
import com.littlebirds.petshop.domain.dtos.pet.PetListDto;
import com.littlebirds.petshop.domain.dtos.pet.PetRegisterDto;
import com.littlebirds.petshop.domain.dtos.user.AdminListDto;
import com.littlebirds.petshop.domain.dtos.user.AdminRegisterDto;
import com.littlebirds.petshop.domain.models.Pet;
import com.littlebirds.petshop.domain.models.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
@SecurityRequirement(name = "bearer-key")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetListDto> petRegister(@RequestBody @Valid PetRegisterDto pet) {

        Pet response = petService.createPet(pet);

        return ResponseEntity.ok().body(new PetListDto(response));
    }

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<PetListDto>> getAllPets(
            @PageableDefault(sort = "name", size = 10) Pageable pagination) {

        Page<PetListDto> response = petService.findAllPets(pagination);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetListDto> getPetByid (@PathVariable Long id)
    {
        var pet = petService.findPetById(id);

        return ResponseEntity.ok().body(new PetListDto(pet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetListDto> editPetInfo (@PathVariable Long id, @RequestBody EditPetInfoDto petInfoDto)
    {
        var pet = petService.editPetById(id,petInfoDto);

        return ResponseEntity.ok().body(new PetListDto(pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetById (@PathVariable Long id)
    {
       petService.deletePet(id);

        return ResponseEntity.noContent().build();
    }

}
