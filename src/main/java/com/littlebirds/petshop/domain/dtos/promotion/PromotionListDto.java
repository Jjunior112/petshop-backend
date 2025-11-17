package com.littlebirds.petshop.domain.dtos.promotion;

import com.littlebirds.petshop.domain.models.Promotion;

public record PromotionListDto(Long id, Integer discount, Boolean isActive, Long serviceId) {
    public PromotionListDto(Promotion promotion) {
        this(
                promotion.getId(),
                promotion.getDiscount(),
                promotion.getIsActive(),
                promotion.getService().getId()
        );
    }
}
