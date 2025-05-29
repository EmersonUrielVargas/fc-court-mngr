package com.foodcourt.court.domain.api;

import com.foodcourt.court.domain.model.Order;

public interface IOrderServicePort {
    void create(Order order);
}
