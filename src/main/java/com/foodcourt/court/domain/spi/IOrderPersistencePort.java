package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.model.Order;

import java.util.List;

public interface IOrderPersistencePort {
    Boolean hasActiveOrdersByClientId(Long clientId, List<String> statusActive);
    void upsertOrder(Order order);
}
