package com.littlebirds.petshop.domain.dtos.scheduling;

import java.time.LocalDateTime;
import java.util.UUID;

public record SchedulingUpdateDto(
        UUID workerId,
        Long petId,
        LocalDateTime date
) {
}
