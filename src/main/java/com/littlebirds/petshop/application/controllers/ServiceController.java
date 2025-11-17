package com.littlebirds.petshop.application.controllers;


import com.littlebirds.petshop.application.services.ServiceService;
import com.littlebirds.petshop.domain.models.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        return ResponseEntity.ok(serviceService.findAllWithPromotions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found")));
    }

    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody Service service) {
        return ResponseEntity.ok(serviceService.save(service));
    }

    @PutMapping("/{id}/apply-best-promotion")
    public ResponseEntity<Service> applyBestPromotion(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.applyBestPromotion(id));
    }

    @PutMapping("/{id}/apply-promotion/{promotionId}")
    public ResponseEntity<Service> applySpecificPromotion(
            @PathVariable Long id, @PathVariable Long promotionId) {
        return ResponseEntity.ok(serviceService.applyPromotion(id, promotionId));
    }
}
