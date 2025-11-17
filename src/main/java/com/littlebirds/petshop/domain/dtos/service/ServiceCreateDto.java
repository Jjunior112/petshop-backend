package com.littlebirds.petshop.domain.dtos.service;

import java.math.BigDecimal;

public record ServiceCreateDto(String name, BigDecimal price, String serviceType) { }

