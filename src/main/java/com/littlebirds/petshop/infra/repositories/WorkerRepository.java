package com.littlebirds.petshop.infra.repositories;

import com.littlebirds.petshop.domain.models.Client;
import com.littlebirds.petshop.domain.models.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WorkerRepository extends JpaRepository<Worker, UUID> {

    Optional<Worker> findByEmail(String email);
}
