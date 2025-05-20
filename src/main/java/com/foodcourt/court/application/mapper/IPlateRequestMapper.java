package com.foodcourt.court.application.mapper;

import com.foodcourt.court.application.dto.request.CreatePlateRequestDto;
import com.foodcourt.court.application.dto.request.RestaurantRequestDto;
import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlateRequestMapper {
    Plate toPlate(CreatePlateRequestDto createPlateRequestDto);
}
