package com.littlebirds.petshop.application.services;

import com.littlebirds.petshop.domain.models.Promotion;
import com.littlebirds.petshop.domain.models.Service;
import com.littlebirds.petshop.infra.repositories.PromotionRepository;
import com.littlebirds.petshop.infra.repositories.ServiceRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final ServiceRepository serviceRepository;

    public PromotionService(PromotionRepository promotionRepository, ServiceRepository serviceRepository) {
        this.promotionRepository = promotionRepository;
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public Promotion createPromotion(Long serviceId, int discount) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Promotion promotion = new Promotion();
        promotion.setService(service);
        promotion.setDiscount(discount);
        promotion.setIsActive(true);

        service.getPromotions().add(promotion);
        return promotionRepository.save(promotion);
    }

    @Transactional
    public Promotion togglePromotionStatus(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        promotion.setIsActive(!promotion.getIsActive());
        return promotionRepository.save(promotion);
    }

    public Optional<Promotion> findById(Long id) {
        return promotionRepository.findById(id);
    }

    public List<Promotion> findAll() {
        return promotionRepository.findAll();
    }

    @Transactional
    public Promotion updatePromotion(Long promotionId, Integer discount, Boolean isActive) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        if (discount != null) {
            promotion.setDiscount(discount);
        }
        if (isActive != null) {
            promotion.setIsActive(isActive);
        }

        return promotionRepository.save(promotion);
    }

    @Transactional
    public void deletePromotion(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        promotionRepository.delete(promotion);
    }
}