package com.foodcourt.court.domain.api;

import com.foodcourt.court.domain.model.Order;
import com.foodcourt.court.domain.utilities.CustomPage;

public interface IOrderServicePort {
    void create(Order order);
    CustomPage<Order> getOrdersByStatus(Long restaurantId, Integer pageSize, Integer page, String status);
    void updateStatusOrder(Long idOrder,String status, String clientCode);

    void cancelOrder(Long idOrder);
}
