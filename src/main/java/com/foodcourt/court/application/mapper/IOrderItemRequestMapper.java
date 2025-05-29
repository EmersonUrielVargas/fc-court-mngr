package com.foodcourt.court.application.mapper;

import com.foodcourt.court.application.dto.request.CreateItemOrderRequestDto;
import com.foodcourt.court.domain.model.OrderPlate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IOrderItemRequestMapper {
    OrderPlate toOrderPlate(CreateItemOrderRequestDto createItemOrderRequestDto);
}
