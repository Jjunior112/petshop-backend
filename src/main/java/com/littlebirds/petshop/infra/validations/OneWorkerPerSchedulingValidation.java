package com.littlebirds.petshop.infra.validations;

import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingRegisterDto;
import com.littlebirds.petshop.infra.repositories.SchedulingRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class OneWorkerPerSchedulingValidation implements Validation{

    private final SchedulingRepository schedulingRepository;

    public OneWorkerPerSchedulingValidation(SchedulingRepository schedulingRepository) {
        this.schedulingRepository = schedulingRepository;
    }

    @Override
    public void validate(SchedulingRegisterDto registerDto) {
        
        var validateScheduling = schedulingRepository.existsByWorkerIdAndDate(registerDto.workerId(),registerDto.date());
        
        if(validateScheduling)
        {
            throw new ValidationException("Funcionário informado já possui agendamento no horário informado");
        }
        
    }
}
