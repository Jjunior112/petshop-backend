package com.littlebirds.petshop.application.services;

import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingRegisterDto;
import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingUpdateDto;
import com.littlebirds.petshop.domain.models.Pet;
import com.littlebirds.petshop.domain.models.Scheduling;
import com.littlebirds.petshop.domain.models.Worker;
import com.littlebirds.petshop.infra.repositories.SchedulingRepository;
import com.littlebirds.petshop.infra.validations.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchedulingService {

    private final SchedulingRepository schedulingRepository;

    private final PetService petService;

    private final UserService userService;

    @Autowired
    private List<Validation> validators;

    public SchedulingService(SchedulingRepository schedulingRepository, PetService petService, UserService userService) {
        this.schedulingRepository = schedulingRepository;
        this.petService = petService;
        this.userService = userService;
    }

    @Transactional
    public Scheduling createScheduling (SchedulingRegisterDto registerDto)
    {

        var worker = (Worker) userService.findUserById(registerDto.workerId());

        var pet = petService.findPetById(registerDto.petId());

        validators.forEach(v -> v.validate(registerDto));

        Scheduling scheduling = new Scheduling(pet,worker,registerDto.date());

        worker.addScheduling(scheduling);

        pet.addScheduling(scheduling);

        return schedulingRepository.save(scheduling);
    }

    public List<Scheduling> findAll() {
        return schedulingRepository.findAll();
    }

    public Scheduling findById(Long id) {
        return schedulingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com o ID: " + id));
    }

    @Transactional
    public Scheduling updateScheduling(Long id, SchedulingUpdateDto dto) {
        Scheduling scheduling = findById(id);

        // Atualiza o trabalhador se informado
        if (dto.workerId() != null) {
            var worker = (Worker) userService.findUserById(dto.workerId());
            scheduling.setWorker(worker);
        }

        // Atualiza o pet se informado
        if (dto.petId() != null) {
            Pet pet = petService.findPetById(dto.petId());
            scheduling.setPet(pet);
        }

        // Atualiza a data se informada
        if (dto.date() != null) {
            scheduling.setDate(dto.date());
        }

        return schedulingRepository.save(scheduling);
    }
    @Transactional
    public void deleteScheduling(Long id)
    {
        var scheduling = schedulingRepository.getReferenceById(id);

        schedulingRepository.delete(scheduling);
    }
}
