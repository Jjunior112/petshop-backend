package com.littlebirds.petshop.domain.dtos.scheduling;

import com.littlebirds.petshop.domain.enums.ServiceType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public record SchedulingRegisterDto(
        UUID workerId,
        Long petId,
        @NotNull
        @NotBlank
        ServiceType serviceType,
        @NotNull
        @Future
        OffsetDateTime date
) {
}
