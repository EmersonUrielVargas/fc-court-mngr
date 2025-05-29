package com.foodcourt.court.application.mapper;

import com.foodcourt.court.application.dto.request.CreateOrderRequestDto;
import com.foodcourt.court.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {IOrderItemRequestMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IOrderRequestMapper {
    Order toOrder(CreateOrderRequestDto createOrderRequestDto);
}
