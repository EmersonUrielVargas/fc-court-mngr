package com.foodcourt.court.infrastructure.out.jpa.repository;

import com.foodcourt.court.infrastructure.out.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(
            "SELECT EXISTS( SELECT o.id " +
            " FROM OrderEntity o" +
            " WHERE o.idCliente = :clientId" +
            " AND o.estado IN (:statusActive))")
    Boolean hasActiveOrdersByClientId(Long clientId, List<String> statusActive);
}
