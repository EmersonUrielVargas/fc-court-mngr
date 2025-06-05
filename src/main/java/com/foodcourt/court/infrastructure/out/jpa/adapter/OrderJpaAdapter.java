package com.foodcourt.court.infrastructure.out.jpa.adapter;

import com.foodcourt.court.domain.model.Order;
import com.foodcourt.court.domain.spi.IOrderPersistencePort;
import com.foodcourt.court.domain.utilities.CustomPage;
import com.foodcourt.court.infrastructure.out.jpa.entity.OrderEntity;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    @Override
    public Boolean hasActiveOrdersByClientId(Long clientId, List<String> statusActive) {
        return orderRepository.hasActiveOrdersByClientId(clientId, statusActive);
    }

    @Override
    public Order upsertOrder(Order order) {
        OrderEntity orderEntities = orderEntityMapper.toOrderEntity(order);
        orderEntities.getPlatos().forEach(item -> item.setPedido(orderEntities));
        return  orderEntityMapper.toOrder(orderRepository.save(orderEntities));
    }

    @Override
    public CustomPage<Order> getOrdersByStatus(Long restaurantId, Integer pageSize, Integer page, String status) {
        Page<OrderEntity> ordersEntity = orderRepository.findByStatus(restaurantId, status, PageRequest.of(page, pageSize));
        CustomPage<Order> orders = new CustomPage<>();
        orders.setData(orderEntityMapper.toOrder(ordersEntity.getContent()));
        orders.setCurrentPage(ordersEntity.getNumber());
        orders.setPageSize(ordersEntity.getSize());
        orders.setTotalPages(ordersEntity.getTotalPages());
        orders.setTotalItems(ordersEntity.getTotalElements());
        orders.setIsLastPage(ordersEntity.isLast());
        return orders;
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId).map(orderEntityMapper::toOrder);
    }

    @Override
    public List<Long> getOrdersIdByRestaurantId(Long restaurantId) {
        return orderRepository.findIdsByRestaurantId(restaurantId);
    }
}
