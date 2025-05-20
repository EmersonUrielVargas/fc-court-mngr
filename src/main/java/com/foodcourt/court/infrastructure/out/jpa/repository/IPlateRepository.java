package com.foodcourt.court.infrastructure.out.jpa.repository;

import com.foodcourt.court.infrastructure.out.jpa.entity.PlateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlateRepository extends JpaRepository<PlateEntity, Long> {
}
