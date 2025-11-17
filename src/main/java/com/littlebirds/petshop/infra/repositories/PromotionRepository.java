package com.littlebirds.petshop.infra.repositories;

import com.littlebirds.petshop.domain.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
