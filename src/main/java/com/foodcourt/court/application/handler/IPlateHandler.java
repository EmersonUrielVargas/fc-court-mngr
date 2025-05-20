package com.foodcourt.court.application.handler;

import com.foodcourt.court.application.dto.request.CreatePlateRequestDto;

public interface IPlateHandler {

    void create(CreatePlateRequestDto createPlateRequestDto);
}
