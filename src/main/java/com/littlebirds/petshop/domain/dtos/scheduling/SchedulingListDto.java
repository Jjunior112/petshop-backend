package com.littlebirds.petshop.domain.dtos.scheduling;

import com.littlebirds.petshop.domain.models.Scheduling;

import java.time.LocalDateTime;
import java.util.UUID;

public record SchedulingListDto(Long id, UUID workerId, Long petId, LocalDateTime date) {
    public SchedulingListDto(Scheduling scheduling) {
        this(scheduling.getId(),scheduling.getWorker().getId(),scheduling.getPet().getId(),scheduling.getDate());
    }
}
