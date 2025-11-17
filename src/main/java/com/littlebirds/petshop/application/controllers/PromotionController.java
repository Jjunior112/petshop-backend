package com.littlebirds.petshop.application.controllers;


import com.littlebirds.petshop.application.services.PromotionService;
import com.littlebirds.petshop.domain.dtos.promotion.PromotionCreateDto;
import com.littlebirds.petshop.domain.dtos.promotion.PromotionListDto;
import com.littlebirds.petshop.domain.dtos.promotion.PromotionUpdateDto;
import com.littlebirds.petshop.domain.models.Promotion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping
    public ResponseEntity<List<PromotionListDto>> getAllPromotions() {
        List<Promotion> promotions = promotionService.findAll();
        return ResponseEntity.ok(
                promotions.stream()
                        .map(PromotionListDto::new)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionListDto> getPromotionById(@PathVariable Long id) {
        Promotion promotion = promotionService.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        return ResponseEntity.ok(new PromotionListDto(promotion));
    }

    @PostMapping
    public ResponseEntity<PromotionListDto> createPromotion(@RequestBody PromotionCreateDto dto) {
        Promotion promotion = promotionService.createPromotion(dto.serviceId(), dto.discount());
        return ResponseEntity.ok(new PromotionListDto(promotion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotionListDto> updatePromotion(
            @PathVariable Long id,
            @RequestBody PromotionUpdateDto dto) {
        Promotion updatedPromotion = promotionService.updatePromotion(id, dto.discount(), dto.isActive());
        return ResponseEntity.ok(new PromotionListDto(updatedPromotion));
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<PromotionListDto> togglePromotion(@PathVariable Long id) {
        Promotion toggledPromotion = promotionService.togglePromotionStatus(id);
        return ResponseEntity.ok(new PromotionListDto(toggledPromotion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }
}