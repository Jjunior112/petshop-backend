package com.littlebirds.petshop.domain.dtos.scheduling;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public record SchedulingUpdateDto(
        UUID workerId,
        Long petId,
        OffsetDateTime date
) {
}
