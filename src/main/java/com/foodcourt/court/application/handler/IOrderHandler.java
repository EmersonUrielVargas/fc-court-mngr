package com.foodcourt.court.application.handler;

import com.foodcourt.court.application.dto.request.CancelOrderRequestDto;
import com.foodcourt.court.application.dto.request.CreateOrderRequestDto;
import com.foodcourt.court.application.dto.request.UpdateStatusOrderRequestDto;
import com.foodcourt.court.application.dto.response.GetOrderResponseDto;
import com.foodcourt.court.domain.utilities.CustomPage;

public interface IOrderHandler {

    void create(CreateOrderRequestDto createOrderRequestDto);

    CustomPage<GetOrderResponseDto> getOrdersByStatus(Long restaurantId, Integer pageSize, Integer page, String status);

    void updateStatusOrder(UpdateStatusOrderRequestDto updateStatusOrderRequestDto);

    void cancelOrder(CancelOrderRequestDto cancelOrderRequestDto);

}
