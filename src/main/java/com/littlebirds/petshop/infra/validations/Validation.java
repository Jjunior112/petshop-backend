package com.littlebirds.petshop.infra.validations;

import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingRegisterDto;

public interface Validation {
    void validate(SchedulingRegisterDto schedulingRegisterDto);
}
