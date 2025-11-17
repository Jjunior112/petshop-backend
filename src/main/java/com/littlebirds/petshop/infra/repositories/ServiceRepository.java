package com.littlebirds.petshop.infra.repositories;

import com.littlebirds.petshop.domain.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}