package com.littlebirds.petshop.infra.validations;

import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingRegisterDto;
import com.littlebirds.petshop.infra.repositories.SchedulingRepository;
import jakarta.validation.ValidationException;

public class OneSchedulingValidation implements Validation{

    private final SchedulingRepository schedulingRepository;

    public OneSchedulingValidation(SchedulingRepository schedulingRepository) {
        this.schedulingRepository = schedulingRepository;
    }

    @Override
    public void validate(SchedulingRegisterDto registerDto) {
        var firstHour = registerDto.date().withHour(7);

        var lastHour = registerDto.date().withHour(18);

        var isOneConsult = schedulingRepository.existsByPetIdAndDateBetween(registerDto.petId(),firstHour,lastHour);

        if(isOneConsult)
        {
            throw new ValidationException("Paciente já possui consulta agendada nesse dia!");
        }
    }

}
