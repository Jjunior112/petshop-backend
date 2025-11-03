package com.littlebirds.petshop.infra.validations;


import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingRegisterDto;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class WorkHourValidation implements Validation {

    public void validate(SchedulingRegisterDto registerDto) {
        var dateScheduling = registerDto.date();

        var sunday = dateScheduling.getDayOfWeek().equals(DayOfWeek.SUNDAY);

        var beforeHour = dateScheduling.getHour() < 7;

        var afterHour = dateScheduling.getHour() > 18;

        if (sunday || beforeHour || afterHour) {
            throw new ValidationException("Agendamento fora do horário de funcionamento da clinica!");
        }


    }
}
