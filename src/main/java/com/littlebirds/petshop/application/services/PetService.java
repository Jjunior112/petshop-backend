package com.littlebirds.petshop.application.services;

import com.littlebirds.petshop.domain.dtos.pet.PetListDto;
import com.littlebirds.petshop.domain.dtos.pet.PetRegisterDto;
import com.littlebirds.petshop.domain.models.Client;
import com.littlebirds.petshop.domain.models.Pet;
import com.littlebirds.petshop.infra.repositories.ClientRepository;
import com.littlebirds.petshop.infra.repositories.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
   private final ClientRepository clientRepository;

    public PetService(PetRepository petRepository, ClientRepository clientRepository) {
        this.petRepository = petRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public Pet createPet(PetRegisterDto petDto) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        Pet newPet = new Pet(petDto, client);

        client.addPet(newPet);

        clientRepository.save(client);

        return newPet;
    }

    @Transactional(readOnly = true)
    public List<PetListDto> findAllPets() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        List<Pet> pets = petRepository.findByClientId(client.getId());

        return pets.stream()
                .map(PetListDto::new)
                .toList();
    }

    public Pet findPetById(Long id)
    {
        return petRepository.getReferenceById(id);
    }

    @Transactional
    public void deletePet(Long id)
    {
        var pet = findPetById(id);

        petRepository.delete(pet);
    }

}
