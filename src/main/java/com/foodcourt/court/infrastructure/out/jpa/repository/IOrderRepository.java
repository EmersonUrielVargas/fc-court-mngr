package com.foodcourt.court.infrastructure.out.jpa.repository;

import com.foodcourt.court.infrastructure.out.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(
            "SELECT EXISTS( SELECT order.id " +
            " FROM OrderEntity order" +
            " WHERE order.idCliente = :clientId" +
            " AND order.estado IN (:statusActive))")
    Boolean hasActiveOrdersByClientId(Long clientId, List<String> statusActive);

    @Query(
            " SELECT order " +
            " FROM OrderEntity order" +
            " WHERE order.restaurante.id = :restaurantId" +
            " AND order.estado LIKE (:status)")
    Page<OrderEntity> findByStatus(Long restaurantId, String status, Pageable pageable);
}
