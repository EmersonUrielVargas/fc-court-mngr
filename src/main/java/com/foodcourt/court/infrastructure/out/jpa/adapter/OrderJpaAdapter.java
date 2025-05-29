package com.foodcourt.court.infrastructure.out.jpa.adapter;

import com.foodcourt.court.domain.model.Order;
import com.foodcourt.court.domain.spi.IOrderPersistencePort;
import com.foodcourt.court.infrastructure.out.jpa.entity.OrderEntity;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    @Override
    public Boolean hasActiveOrdersByClientId(Long clientId, List<String> statusActive) {
        return orderRepository.hasActiveOrdersByClientId(clientId, statusActive);
    }

    @Override
    public void upsertOrder(Order order) {
        OrderEntity orderEntities = orderEntityMapper.toOrderEntity(order);
        orderEntities.getPlatos().forEach(item -> item.setPedido(orderEntities));
        orderRepository.save(orderEntities);
    }
}
