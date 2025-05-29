package com.foodcourt.court.infrastructure.out.jpa.repository;

import com.foodcourt.court.infrastructure.out.jpa.entity.PlateEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPlateRepository extends JpaRepository<PlateEntity, Long> {
    @Query(
            "SELECT pl " +
            " FROM PlateEntity pl" +
            " WHERE pl.restaurante.id = :restaurantId")
    List<PlateEntity> findByRestaurant(Long restaurantId, Pageable pageable);

    @Query(
            "SELECT pl " +
            " FROM PlateEntity pl" +
            " WHERE pl.restaurante.id = :restaurantId" +
            " AND pl.categoria.id = :categoryId")
    List<PlateEntity> findByRestaurantAndCategory(Long restaurantId, Long categoryId, Pageable pageable);

    @Query(
            "SELECT pl.id " +
            " FROM PlateEntity pl" +
            " WHERE pl.restaurante.id = :restaurantId" +
            " AND pl.id IN (:plateIds)")
    List<Long> findExistingPlateIdsInRestaurant(Long restaurantId, List<Long> plateIds);
}
