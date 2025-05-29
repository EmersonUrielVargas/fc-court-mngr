package com.foodcourt.court.infrastructure.out.jpa.mapper;

import com.foodcourt.court.domain.model.Order;
import com.foodcourt.court.infrastructure.out.jpa.entity.OrderEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {IOrderPlateEntityMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderEntityMapper {
    @Mapping(source = "clientId", target = "idCliente")
    @Mapping(source = "date", target = "fecha")
    @Mapping(source = "chefId", target = "idChef")
    @Mapping(source = "restaurantId", target = "restaurante.id")
    @Mapping(source = "codeValidation", target = "codigo")
    @Mapping(source = "orderPlates", target = "platos")
    OrderEntity toOrderEntity(Order order);
    List<OrderEntity> toOrderEntity(List<Order> order);

    @InheritInverseConfiguration
    Order toOrder(OrderEntity orderEntity);

}
