package com.littlebirds.petshop.application.controllers;

import com.littlebirds.petshop.application.services.SchedulingService;
import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingListDto;
import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingRegisterDto;
import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingUpdateDto;
import com.littlebirds.petshop.domain.models.Scheduling;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<SchedulingListDto>> findAll() {
        List<Scheduling> schedulings = schedulingService.findAll();
        List<SchedulingListDto> dtos = schedulings.stream()
                .map(SchedulingListDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchedulingListDto> findById(@PathVariable Long id) {
        Scheduling scheduling = schedulingService.findById(id);
        SchedulingListDto dto = new SchedulingListDto(scheduling);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchedulingListDto> updateScheduling(
            @PathVariable Long id,
            @RequestBody SchedulingUpdateDto updateDto
    ) {
        Scheduling updated = schedulingService.updateScheduling(id, updateDto);
        SchedulingListDto dto = new SchedulingListDto(updated);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduling(@PathVariable Long id) {
        schedulingService.deleteScheduling(id);
        return ResponseEntity.noContent().build();
    }
}
