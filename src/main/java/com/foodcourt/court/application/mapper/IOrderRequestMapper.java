package com.foodcourt.court.application.mapper;

import com.foodcourt.court.application.dto.request.CreateOrderRequestDto;
import com.foodcourt.court.application.dto.response.GetOrderResponseDto;
import com.foodcourt.court.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {IOrderItemRequestMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IOrderRequestMapper {
    Order toOrder(CreateOrderRequestDto createOrderRequestDto);

    @Mapping(source = "status.message", target = "status")
    GetOrderResponseDto toGetOrderResponseDto(Order order);
    List<GetOrderResponseDto> toGetOrderResponseDto(List<Order> order);
}
