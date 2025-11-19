package com.littlebirds.petshop.application.controllers;


import com.littlebirds.petshop.application.services.ServiceService;
import com.littlebirds.petshop.domain.dtos.promotion.PromotionListDto;
import com.littlebirds.petshop.domain.dtos.service.ServiceCreateDto;
import com.littlebirds.petshop.domain.dtos.service.ServiceListDto;
import com.littlebirds.petshop.domain.enums.ServiceType;
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
    public ResponseEntity<List<ServiceListDto>> getAllServices() {
        List<ServiceListDto> dtos = serviceService.findAllWithPromotions();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceListDto> getServiceById(@PathVariable Long id) {
        Service service = serviceService.findById(id);

        return ResponseEntity.ok(new ServiceListDto(
                service.getId(),
                service.getServiceName(),
                service.getPrice(),
                service.getServiceType().name(),
                service.getPromotions().stream()
                        .map(p -> new PromotionListDto(p.getId(), p.getDiscount(), p.getIsActive(),p.getService().getId()))
                        .toList()
        ));
    }

    @PostMapping
    public ResponseEntity<ServiceListDto> createService(@RequestBody ServiceCreateDto dto) {
        Service service = new Service();
        service.setServiceName(dto.name());
        service.setPrice(dto.price());
        service.setServiceType(ServiceType.valueOf(dto.serviceType())); // Converte string para enum

        Service saved = serviceService.save(service);

        return ResponseEntity.ok(new ServiceListDto(
                saved.getId(),
                saved.getServiceName(),
                saved.getPrice(),
                saved.getServiceType().name(),
                List.of()
        ));
    }

    @PutMapping("/{id}/apply-best-promotion")
    public ResponseEntity<ServiceListDto> applyBestPromotion(@PathVariable Long id) {
        Service service = serviceService.applyBestPromotion(id);

        return ResponseEntity.ok(new ServiceListDto(
                service.getId(),
                service.getServiceName(),
                service.getPrice(),
                service.getServiceType().name(),
                service.getPromotions().stream()
                        .map(p -> new PromotionListDto(p.getId(), p.getDiscount(), p.getIsActive(),p.getService().getId()))
                        .toList()
        ));
    }

    @PutMapping("/{id}/apply-promotion/{promotionId}")
    public ResponseEntity<ServiceListDto> applySpecificPromotion(
            @PathVariable Long id, @PathVariable Long promotionId) {

        Service service = serviceService.applyPromotion(id, promotionId);

        return ResponseEntity.ok(new ServiceListDto(
                service.getId(),
                service.getServiceName(),
                service.getPrice(),
                service.getServiceType().name(),
                service.getPromotions().stream()
                        .map(p -> new PromotionListDto(p.getId(), p.getDiscount(), p.getIsActive(),p.getService().getId()))
                        .toList()
        ));
    }
}
