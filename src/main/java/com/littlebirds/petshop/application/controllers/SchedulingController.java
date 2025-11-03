package com.littlebirds.petshop.application.controllers;

import com.littlebirds.petshop.application.services.SchedulingService;
import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingRegisterDto;
import com.littlebirds.petshop.domain.dtos.scheduling.SchedulingUpdateDto;
import com.littlebirds.petshop.domain.models.Scheduling;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedulings")
public class SchedulingController {

    private final SchedulingService schedulingService;

    public SchedulingController(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @PostMapping
    public ResponseEntity<Scheduling> createScheduling(@RequestBody SchedulingRegisterDto registerDto) {
        Scheduling scheduling = schedulingService.createScheduling(registerDto);
        return ResponseEntity.ok(scheduling);
    }

    @GetMapping
    public ResponseEntity<List<Scheduling>> findAll() {
        List<Scheduling> schedulings = schedulingService.findAll();
        return ResponseEntity.ok(schedulings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scheduling> findById(@PathVariable Long id) {
        Scheduling scheduling = schedulingService.findById(id);
        return ResponseEntity.ok(scheduling);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Scheduling> updateScheduling(
            @PathVariable Long id,
            @RequestBody SchedulingUpdateDto updateDto
    ) {
        Scheduling updated = schedulingService.updateScheduling(id, updateDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduling(@PathVariable Long id) {
        schedulingService.deleteScheduling(id);
        return ResponseEntity.noContent().build();
    }
}