package com.littlebirds.petshop.infra.repositories;

import com.littlebirds.petshop.domain.models.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet,Long> {
    Page<Pet> findByClientId(UUID clientId, Pageable pageable);;
}
