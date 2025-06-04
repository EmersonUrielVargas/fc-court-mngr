package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.model.Order;
import com.foodcourt.court.domain.utilities.CustomPage;

import java.util.List;
import java.util.Optional;

public interface IOrderPersistencePort {
    Boolean hasActiveOrdersByClientId(Long clientId, List<String> statusActive);
    Order upsertOrder(Order order);
    CustomPage<Order> getOrdersByStatus(Long restaurantId, Integer pageSize, Integer page, String status);
    Optional<Order> findById(Long orderId);

}
