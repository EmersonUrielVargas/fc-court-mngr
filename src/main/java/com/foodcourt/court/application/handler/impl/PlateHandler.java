package com.foodcourt.court.application.handler.impl;

import com.foodcourt.court.application.dto.request.CreatePlateRequestDto;
import com.foodcourt.court.application.dto.request.StatusPlateRequestDto;
import com.foodcourt.court.application.dto.request.UpdatePlateRequestDto;
import com.foodcourt.court.application.dto.response.PlatesByRestaurantResponseDto;
import com.foodcourt.court.application.handler.IPlateHandler;
import com.foodcourt.court.application.mapper.IPlateRequestMapper;
import com.foodcourt.court.domain.api.IPlateServicePort;
import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.utilities.CustomPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlateHandler implements IPlateHandler {
    private final IPlateServicePort plateServicePort;
    private final IPlateRequestMapper plateRequestMapper;

    @Override
    public void create(CreatePlateRequestDto createPlateRequestDto) {
        Plate plate = plateRequestMapper.toPlate(createPlateRequestDto);
        plateServicePort.create(plate);
    }

    @Override
    public void update(UpdatePlateRequestDto updatePlateRequestDto) {
        Plate plate = plateRequestMapper.toPlate(updatePlateRequestDto);
        plateServicePort.update(plate);
    }

    @Override
    public void setStatus(StatusPlateRequestDto statusPlateRequestDto) {
        Plate plate = plateRequestMapper.toPlate(statusPlateRequestDto);
        plateServicePort.setActive(plate);
    }

    @Override
    public CustomPage<PlatesByRestaurantResponseDto> getPlatesByRestaurant(Long restaurantId, Integer pageSize, Integer page, Long categoryId) {
        CustomPage<Plate> pagePlates = plateServicePort.getPlatesByRestaurant(restaurantId, pageSize, page, categoryId);
        return new CustomPage<>(plateRequestMapper.toPlatesByRestaurantResponseDto(pagePlates.getData()), pagePlates);
    }
}
