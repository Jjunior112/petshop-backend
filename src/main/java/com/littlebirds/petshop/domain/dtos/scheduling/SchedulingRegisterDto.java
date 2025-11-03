package com.littlebirds.petshop.domain.dtos.scheduling;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record SchedulingRegisterDto(
        UUID workerId,
        Long petId,
        @NotNull
        @Future
        LocalDateTime date
) {
}
