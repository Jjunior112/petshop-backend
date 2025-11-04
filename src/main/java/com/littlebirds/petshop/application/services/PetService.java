package com.littlebirds.petshop.application.services;

import com.littlebirds.petshop.domain.dtos.pet.EditPetInfoDto;
import com.littlebirds.petshop.domain.dtos.pet.PetListDto;
import com.littlebirds.petshop.domain.dtos.pet.PetRegisterDto;
import com.littlebirds.petshop.domain.models.Client;
import com.littlebirds.petshop.domain.models.Pet;
import com.littlebirds.petshop.infra.repositories.ClientRepository;
import com.littlebirds.petshop.infra.repositories.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
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
    public Page<PetListDto> findAllPets(Pageable pageable) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        Page<Pet> pets = petRepository.findByClientId(client.getId(), pageable);

        return pets.map(PetListDto::new);
    }

    @Transactional(readOnly = true)
    public Pet findPetById(Long id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado."));

        boolean isAdminOrWorker = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN") || role.equals("ROLE_WORKER"));

        boolean isOwner = pet.getClient().getEmail().equalsIgnoreCase(userEmail);

        if (!isAdminOrWorker && !isOwner) {
            throw new ValidationException("Você não tem permissão para visualizar este pet.");
        }

        return pet;
    }

    @Transactional
    public Pet editPetById(Long id, EditPetInfoDto petInfoDto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Pet pet = findPetById(id);

        boolean isAdminOrWorker = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN") || role.equals("ROLE_WORKER"));

        boolean isOwner = pet.getClient().getEmail().equalsIgnoreCase(userEmail);

        if (!isAdminOrWorker && !isOwner) {
            throw new ValidationException("Você não tem permissão para editar este pet.");
        }

        pet.editPetInfo(petInfoDto);

        return petRepository.save(pet);
    }

    // 🔹 Exclusão de pet — apenas o dono ou ADMIN/WORKER
    @Transactional
    public void deletePet(Long id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Pet pet = findPetById(id);

        boolean isAdminOrWorker = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN") || role.equals("ROLE_WORKER"));

        boolean isOwner = pet.getClient().getEmail().equalsIgnoreCase(userEmail);

        if (!isAdminOrWorker && !isOwner) {
            throw new ValidationException("Você não tem permissão para excluir este pet.");
        }

        petRepository.delete(pet);
    }
}
