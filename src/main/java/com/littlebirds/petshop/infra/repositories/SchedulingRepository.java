package com.littlebirds.petshop.infra.repositories;

import com.littlebirds.petshop.domain.enums.SchedulingStatus;
import com.littlebirds.petshop.domain.models.Scheduling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    Page<Scheduling> findByWorkerId(UUID workerId, Pageable pageable);

    Page<Scheduling> findByWorkerIdAndStatus(UUID workerId, SchedulingStatus status, Pageable pageable);

    Page<Scheduling> findByStatus(SchedulingStatus status, Pageable pageable);

    Page<Scheduling> findByClientEmail(String email, Pageable pageable);

    Page<Scheduling> findByClientEmailAndStatus(String email, SchedulingStatus status, Pageable pageable);

    Boolean existsByWorkerIdAndDate(UUID id, OffsetDateTime date);

    Boolean existsByPetIdAndDateBetween(Long id, OffsetDateTime firstHour, OffsetDateTime lastHour);
}
