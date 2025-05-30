package com.foodcourt.court.application.handler.impl;

import com.foodcourt.court.application.dto.request.CreateOrderRequestDto;
import com.foodcourt.court.application.dto.response.GetOrderResponseDto;
import com.foodcourt.court.application.handler.IOrderHandler;
import com.foodcourt.court.application.mapper.IOrderRequestMapper;
import com.foodcourt.court.domain.api.IOrderServicePort;
import com.foodcourt.court.domain.model.Order;
import com.foodcourt.court.domain.utilities.CustomPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderHandler implements IOrderHandler {
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderServicePort orderServicePort;

    @Override
    public void create(CreateOrderRequestDto createOrderRequestDto) {
        Order order = orderRequestMapper.toOrder(createOrderRequestDto);
        orderServicePort.create(order);
    }

    @Override
    public CustomPage<GetOrderResponseDto> getOrdersByStatus(Long restaurantId, Integer pageSize, Integer page, String status) {
        CustomPage<Order> pageOrders = orderServicePort.getOrdersByStatus(restaurantId, pageSize, page, status);
        return new CustomPage<>(orderRequestMapper.toGetOrderResponseDto(pageOrders.getData()), pageOrders);
    }
}
