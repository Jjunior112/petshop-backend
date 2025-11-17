package com.littlebirds.petshop.domain.dtos.service;

import com.littlebirds.petshop.domain.dtos.promotion.PromotionListDto;

import java.math.BigDecimal;
import java.util.List;

public record ServiceListDto(Long id, String name, BigDecimal price, String serviceType, List<PromotionListDto> promotions) { }
