package com.foodcourt.court.application.mapper;

import com.foodcourt.court.application.dto.request.CreatePlateRequestDto;
import com.foodcourt.court.application.dto.request.StatusPlateRequestDto;
import com.foodcourt.court.application.dto.request.UpdatePlateRequestDto;
import com.foodcourt.court.domain.model.Plate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IPlateRequestMapper {
    Plate toPlate(CreatePlateRequestDto createPlateRequestDto);
    Plate toPlate(UpdatePlateRequestDto updatePlateRequestDto);
    Plate toPlate(StatusPlateRequestDto statusPlateRequestDto);
}
