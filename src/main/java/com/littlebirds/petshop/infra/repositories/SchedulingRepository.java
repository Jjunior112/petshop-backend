package com.littlebirds.petshop.infra.repositories;

import com.littlebirds.petshop.domain.enums.SchedulingStatus;
import com.littlebirds.petshop.domain.models.Scheduling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    Page<Scheduling> findByStatus(SchedulingStatus status, Pageable pageable);

    Page<Scheduling> findByClientEmail(String email, Pageable pageable);

    Page<Scheduling> findByClientEmailAndStatus(String email, SchedulingStatus status, Pageable pageable);

    Boolean existsByWorkerIdAndDate(UUID id, LocalDateTime date);

    Boolean existsByPetIdAndDateBetween(Long id, LocalDateTime firstHour, LocalDateTime lastHour);
}
