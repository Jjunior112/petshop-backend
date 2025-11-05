package com.littlebirds.petshop.domain.dtos.scheduling;

import com.littlebirds.petshop.domain.enums.SchedulingStatus;
import com.littlebirds.petshop.domain.enums.ServiceType;
import com.littlebirds.petshop.domain.models.Scheduling;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public record SchedulingListDto(
        Long id,
        String clientName,
        Long petId,
        String petName,
        ServiceType serviceType,
        UUID workerId,
        String workerName,
        OffsetDateTime date,
        SchedulingStatus status
) {
    public SchedulingListDto(Scheduling scheduling) {
        this(
                scheduling.getId(),
                scheduling.getClientName(),
                scheduling.getPet().getId(),
                scheduling.getPet().getName(),
                scheduling.getServiceType(),
                scheduling.getWorker().getId(),
                scheduling.getWorker().getFullName(),
                scheduling.getDate(),
                scheduling.getStatus()
        );
    }
}