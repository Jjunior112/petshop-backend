package com.littlebirds.petshop.infra.repositories;

import com.littlebirds.petshop.domain.models.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    Boolean existsByWorkerIdAndDate(UUID id, LocalDateTime date);

    Boolean existsByPetIdAndDateBetween(Long id, LocalDateTime firstHour, LocalDateTime lastHour);
}
