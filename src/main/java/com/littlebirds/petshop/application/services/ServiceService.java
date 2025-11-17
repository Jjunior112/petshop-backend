package com.littlebirds.petshop.application.services;

import com.littlebirds.petshop.domain.models.Promotion;
import com.littlebirds.petshop.domain.models.Service;
import com.littlebirds.petshop.infra.repositories.PromotionRepository;
import com.littlebirds.petshop.infra.repositories.ServiceRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final PromotionRepository promotionRepository;

    public ServiceService(ServiceRepository serviceRepository, PromotionRepository promotionRepository) {
        this.serviceRepository = serviceRepository;
        this.promotionRepository = promotionRepository;
    }

    @Transactional
    public Service applyPromotion(Long serviceId, Long promotionId) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        if (!promotion.getService().getId().equals(serviceId)) {
            throw new IllegalArgumentException("Promotion does not belong to this service.");
        }

        if (Boolean.FALSE.equals(promotion.getIsActive())) {
            throw new IllegalStateException("Promotion is inactive.");
        }

        service.applyDiscount(promotion);
        return serviceRepository.save(service);
    }

    @Transactional
    public Service applyBestPromotion(Long serviceId) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        service.applyBestDiscount();
        return serviceRepository.save(service);
    }

    public List<Service> findAllWithPromotions() {
        return serviceRepository.findAll();
    }

    public Optional<Service> findById(Long id) {
        return serviceRepository.findById(id);
    }

    @Transactional
    public Service save(Service service) {
        return serviceRepository.save(service);
    }
}

