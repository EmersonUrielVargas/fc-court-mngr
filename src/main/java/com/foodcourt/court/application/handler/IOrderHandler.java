package com.foodcourt.court.application.handler;

import com.foodcourt.court.application.dto.request.CreateOrderRequestDto;

public interface IOrderHandler {

    void create(CreateOrderRequestDto createOrderRequestDto);

}
