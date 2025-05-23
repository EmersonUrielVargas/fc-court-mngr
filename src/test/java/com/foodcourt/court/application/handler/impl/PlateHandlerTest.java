package com.foodcourt.court.application.handler.impl;

import com.foodcourt.court.application.dto.request.CreatePlateRequestDto;
import com.foodcourt.court.application.dto.request.UpdatePlateRequestDto;
import com.foodcourt.court.application.mapper.IPlateRequestMapper;
import com.foodcourt.court.domain.api.IPlateServicePort;
import com.foodcourt.court.domain.model.Plate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlateHandlerTest {

    @Mock
    private IPlateServicePort plateServicePort;

    @Mock
    private IPlateRequestMapper plateRequestMapper;

    @InjectMocks
    private PlateHandler plateHandler;

    @Test
    void createPlateSuccessful() {
        CreatePlateRequestDto plateRequestDto =  new CreatePlateRequestDto();
        Long ownerId = 10L;

        Plate plate = new Plate();

        when(plateRequestMapper.toPlate(plateRequestDto)).thenReturn(plate);
        plateHandler.create(plateRequestDto,ownerId);

        verify(plateRequestMapper, times(1)).toPlate(plateRequestDto);
        verify(plateServicePort, times(1)).create(any(Plate.class), anyLong());
    }

    @Test
    void updatePlateSuccessful() {
        Long ownerId = 10L;
        UpdatePlateRequestDto plateRequestDto =  new UpdatePlateRequestDto();


        Plate plate = new Plate();

        when(plateRequestMapper.toPlate(plateRequestDto)).thenReturn(plate);
        plateHandler.update(plateRequestDto,ownerId);

        verify(plateRequestMapper, times(1)).toPlate(plateRequestDto);
        verify(plateServicePort, times(1)).update(any(Plate.class), anyLong());
    }
}