package com.littlebirds.petshop.infra.repositories;

import com.littlebirds.petshop.domain.models.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkerRepository extends JpaRepository<Worker, UUID> {
}
