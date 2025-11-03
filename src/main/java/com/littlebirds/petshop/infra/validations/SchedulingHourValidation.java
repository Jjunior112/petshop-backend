package com.littlebirds.petshop.infra.validations;

import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingRegisterDto;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class SchedulingHourValidation implements Validation{

    public void validate(SchedulingRegisterDto registerDto) {
        var dateScheduling = registerDto.date();

        var now = LocalDateTime.now();

        var diference = Duration.between(now, dateScheduling).toMinutes();

        if (diference < 30) {
            throw new ValidationException("A consulta deve ser agendada com 30 minutos de antecedência!");
        }

    }
}