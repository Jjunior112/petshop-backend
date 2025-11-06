package com.littlebirds.petshop.domain.dtos.scheduling;

import com.littlebirds.petshop.domain.enums.ServiceType;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public record SchedulingUpdateDto(
        UUID workerId,
        ServiceType serviceType,
        OffsetDateTime date
) {
}
