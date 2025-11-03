package com.littlebirds.petshop.domain.dtos.scheduling;

import com.littlebirds.petshop.domain.models.Scheduling;

import java.time.LocalDateTime;
import java.util.UUID;

public record SchedulingListDto(
        Long id,
        Long petId,
        String petName,
        UUID workerId,
        String workerName,
        LocalDateTime date
) {
    public SchedulingListDto(Scheduling scheduling) {
        this(
                scheduling.getId(),
                scheduling.getPet().getId(),
                scheduling.getPet().getName(),
                scheduling.getWorker().getId(),
                scheduling.getWorker().getFullName(),
                scheduling.getDate()
        );
    }
}