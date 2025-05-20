package com.foodcourt.court.infrastructure.out.jpa.repository;

import com.foodcourt.court.infrastructure.out.jpa.entity.CategoryEntity;
import com.foodcourt.court.infrastructure.out.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
