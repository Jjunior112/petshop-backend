package com.littlebirds.petshop.application.controllers;

import com.littlebirds.petshop.application.services.SchedulingService;
import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingListDto;
import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingRegisterDto;
import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingUpdateDto;
import com.littlebirds.petshop.domain.enums.SchedulingStatus;
import com.littlebirds.petshop.domain.models.Scheduling;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/schedulings")
public class SchedulingController {

    private final SchedulingService schedulingService;

    public SchedulingController(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @PostMapping
    public ResponseEntity<SchedulingListDto> createScheduling(@RequestBody SchedulingRegisterDto registerDto) {
        Scheduling scheduling = schedulingService.createScheduling(registerDto);
        SchedulingListDto dto = new SchedulingListDto(scheduling);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<SchedulingListDto>> getAllSchedulings(
            @PageableDefault(sort = "date", size = 10) Pageable pagination,
            @RequestParam(name = "status", required = false) SchedulingStatus status) {

        Page<SchedulingListDto> response = schedulingService.findAllSchedulings(pagination, status);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchedulingListDto> findById(@PathVariable Long id) {
        Scheduling scheduling = schedulingService.findById(id);
        SchedulingListDto dto = new SchedulingListDto(scheduling);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchedulingListDto> updateScheduling(@PathVariable Long id, @RequestBody SchedulingUpdateDto updateDto)
    {
        Scheduling updated = schedulingService.updateScheduling(id, updateDto);
        SchedulingListDto dto = new SchedulingListDto(updated);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<SchedulingListDto> updateSchedulingStatus(@PathVariable Long id)
    {
        Scheduling updated = schedulingService.updateSchedulingStatus(id);
        SchedulingListDto dto = new SchedulingListDto(updated);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduling(@PathVariable Long id) {
        schedulingService.deleteScheduling(id);
        return ResponseEntity.noContent().build();
    }
}
