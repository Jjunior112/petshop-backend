package com.littlebirds.petshop.application.controllers;


import com.littlebirds.petshop.application.services.PromotionService;
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
    public ResponseEntity<List<Promotion>> getAllPromotions() {
        return ResponseEntity.ok(promotionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable Long id) {
        return ResponseEntity.ok(promotionService.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found")));
    }

    @PostMapping("/{serviceId}")
    public ResponseEntity<Promotion> createPromotion(
            @PathVariable Long serviceId, @RequestParam int discount) {
        return ResponseEntity.ok(promotionService.createPromotion(serviceId, discount));
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<Promotion> togglePromotion(@PathVariable Long id) {
        return ResponseEntity.ok(promotionService.togglePromotionStatus(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promotion> updatePromotion(
            @PathVariable Long id,
            @RequestParam(required = false) Integer discount,
            @RequestParam(required = false) Boolean isActive) {
        return ResponseEntity.ok(promotionService.updatePromotion(id, discount, isActive));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }
}