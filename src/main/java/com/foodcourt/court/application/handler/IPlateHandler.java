package com.foodcourt.court.application.handler;

import com.foodcourt.court.application.dto.request.CreatePlateRequestDto;
import com.foodcourt.court.application.dto.request.StatusPlateRequestDto;
import com.foodcourt.court.application.dto.request.UpdatePlateRequestDto;

public interface IPlateHandler {

    void create(CreatePlateRequestDto createPlateRequestDto, Long ownerId);
    void update(UpdatePlateRequestDto updatePlateRequestDto, Long ownerId);
    void setStatus(StatusPlateRequestDto statusPlateRequestDto, Long ownerId);
}
