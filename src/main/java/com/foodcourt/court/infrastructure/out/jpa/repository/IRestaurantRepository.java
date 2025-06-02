package com.foodcourt.court.infrastructure.out.jpa.repository;

import com.foodcourt.court.infrastructure.out.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    Optional<RestaurantEntity> findByIdPropietario(Long ownerId);
}
