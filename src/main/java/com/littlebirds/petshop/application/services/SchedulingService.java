package com.littlebirds.petshop.application.services;

import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingListDto;
import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingRegisterDto;
import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingUpdateDto;
import com.littlebirds.petshop.domain.enums.SchedulingStatus;
import com.littlebirds.petshop.domain.models.Pet;
import com.littlebirds.petshop.domain.models.Scheduling;
import com.littlebirds.petshop.domain.models.Worker;
import com.littlebirds.petshop.infra.repositories.SchedulingRepository;
import com.littlebirds.petshop.infra.repositories.UserRepository;
import com.littlebirds.petshop.infra.repositories.WorkerRepository;
import com.littlebirds.petshop.infra.validations.Validation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchedulingService {

    private final SchedulingRepository schedulingRepository;

    private final PetService petService;

    private final WorkerRepository workerRepository;



    @Autowired
    private List<Validation> validators;

    public SchedulingService(SchedulingRepository schedulingRepository, PetService petService, WorkerRepository workerRepository) {
        this.schedulingRepository = schedulingRepository;
        this.petService = petService;
        this.workerRepository = workerRepository;
    }

    @Transactional
    public Scheduling createScheduling(SchedulingRegisterDto registerDto) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        var worker = workerRepository.findById(registerDto.workerId())
                .orElseThrow(() -> new ValidationException("Funcionário informado não existe."));

        var pet = petService.findPetById(registerDto.petId());

        if (pet.getClient() == null || !pet.getClient().getEmail().equalsIgnoreCase(userEmail)) {
            throw new ValidationException("Você não tem permissão para agendar serviços para este pet.");
        }

        validators.forEach(v -> v.validate(registerDto));

        Scheduling scheduling = new Scheduling(pet, registerDto.serviceType(), pet.getClient().getFullName(), userEmail, worker, registerDto.date());

        worker.addScheduling(scheduling);
        pet.addScheduling(scheduling);

        return schedulingRepository.save(scheduling);
    }

    public Page<SchedulingListDto> findAllSchedulings(Pageable pageable, SchedulingStatus status) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        boolean isAdminOrWorker = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN") || role.equals("ROLE_WORKER"));

        Page<Scheduling> schedulings;

        if (isAdminOrWorker) {
            schedulings = (status != null)
                    ? schedulingRepository.findByStatus(status, pageable)
                    : schedulingRepository.findAll(pageable);
        } else {

            schedulings = (status != null)
                    ? schedulingRepository.findByClientEmailAndStatus(userEmail, status, pageable)
                    : schedulingRepository.findByClientEmail(userEmail, pageable);
        }

        return schedulings.map(SchedulingListDto::new);
    }

    public Scheduling findById(Long id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        var scheduling = schedulingRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Agendamento não encontrado com o ID: " + id));

        boolean isAdminOrWorker = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN") || role.equals("ROLE_WORKER"));

        boolean isOwner = scheduling.getPet().getClient().getEmail().equalsIgnoreCase(userEmail);

        if (!isAdminOrWorker && !isOwner) {
            throw new ValidationException("Você não tem permissão para visualizar este agendamento.");
        }

        return scheduling;
    }

    @Transactional
    public Scheduling updateScheduling(Long id, SchedulingUpdateDto dto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Scheduling scheduling = findById(id);

        boolean isAdminOrWorker = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN") || role.equals("ROLE_WORKER"));

        boolean isOwner = scheduling.getPet().getClient().getEmail().equalsIgnoreCase(userEmail);

        if (!isAdminOrWorker && !isOwner) {
            throw new ValidationException("Você não tem permissão para atualizar este agendamento.");
        }

        if (dto.workerId() != null) {
            if (!isAdminOrWorker) {
                throw new ValidationException("Somente administradores ou funcionários podem alterar o trabalhador do agendamento.");
            }
            var worker = workerRepository.getReferenceById(dto.workerId());
            scheduling.setWorker(worker);
        }

        if (dto.petId() != null) {
            var pet = petService.findPetById(dto.petId());

            boolean isPetOwner = pet.getClient().getEmail().equalsIgnoreCase(userEmail);
            if (!isAdminOrWorker && !isPetOwner) {
                throw new ValidationException("Você não tem permissão para vincular este pet ao agendamento.");
            }

            scheduling.setPet(pet);
        }

        if (dto.date() != null) {
            scheduling.setDate(dto.date());
        }

        return schedulingRepository.save(scheduling);
    }

    @Transactional
    public Scheduling updateSchedulingStatus(Long id) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasPermission = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN") || role.equals("ROLE_WORKER"));

        if (!hasPermission) {
            throw new AccessDeniedException("Acesso negado: apenas ADMIN ou WORKER podem finalizar agendamentos.");
        }

        Scheduling scheduling = findById(id);
        scheduling.changeStatus(SchedulingStatus.COMPLETED);

        return schedulingRepository.save(scheduling);
    }

    @Transactional
    public void deleteScheduling(Long id) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        var scheduling = schedulingRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Agendamento não encontrado."));

        boolean isAdminOrWorker = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN") || role.equals("ROLE_WORKER"));

        boolean isOwner = scheduling.getPet().getClient().getEmail().equalsIgnoreCase(userEmail);

        if (!isAdminOrWorker && !isOwner) {
            throw new ValidationException("Você não tem permissão para cancelar este agendamento.");
        }

        if (scheduling.getStatus() != SchedulingStatus.PENDING) {
            throw new ValidationException("Somente agendamentos pendentes podem ser cancelados.");
        }

        scheduling.changeStatus(SchedulingStatus.CANCELED);
        schedulingRepository.save(scheduling);
    }
}
