package com.foodcourt.court.application.handler;

import com.foodcourt.court.application.dto.request.CreatePlateRequestDto;
import com.foodcourt.court.application.dto.request.RestaurantRequestDto;

public interface IPlateHandler {

    void create(CreatePlateRequestDto createPlateRequestDto);
}
