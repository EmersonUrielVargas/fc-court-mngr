package com.foodcourt.court.application.handler;

import com.foodcourt.court.application.dto.request.CreatePlateRequestDto;
import com.foodcourt.court.application.dto.request.StatusPlateRequestDto;
import com.foodcourt.court.application.dto.request.UpdatePlateRequestDto;
import com.foodcourt.court.application.dto.response.PlatesByRestaurantResponseDto;

import java.util.List;

public interface IPlateHandler {

    void create(CreatePlateRequestDto createPlateRequestDto);
    void update(UpdatePlateRequestDto updatePlateRequestDto);
    void setStatus(StatusPlateRequestDto statusPlateRequestDto);
    List<PlatesByRestaurantResponseDto> getPlatesByRestaurant(Long restaurantId, Integer pageSize, Integer page, Long categoryId);

}
