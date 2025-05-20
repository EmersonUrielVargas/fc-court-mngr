package com.foodcourt.court.application.mapper;

import com.foodcourt.court.application.dto.request.CreatePlateRequestDto;
import com.foodcourt.court.domain.model.Plate;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IPlateRequestMapperTest {

    private IPlateRequestMapper plateRequestMapper = Mappers.getMapper(IPlateRequestMapper.class);

    @Test
    void toPlateSuccessful() {
        CreatePlateRequestDto createPlateRequestDto = new CreatePlateRequestDto();
        createPlateRequestDto.setName("Pizza Napolitana");
        createPlateRequestDto.setPrice(30000);
        createPlateRequestDto.setCategoryId(1L);
        createPlateRequestDto.setDescription("Clásica pizza napolitana con tomate, mozzarella, albahaca y aceite de oliva.");
        createPlateRequestDto.setUrlImage("https://example.com/images/napolitana.jpg");
        createPlateRequestDto.setRestaurantId(101L);

        Plate plate = plateRequestMapper.toPlate(createPlateRequestDto);

        assertAll("validation mapping RestaurantRequestDto into Restaurant",
                ()->assertNotNull(plate),
                ()->assertEquals(createPlateRequestDto.getName(), plate.getName()),
                ()->assertEquals(createPlateRequestDto.getCategoryId(), plate.getCategoryId()),
                ()->assertEquals(createPlateRequestDto.getDescription(), plate.getDescription()),
                ()->assertEquals(createPlateRequestDto.getUrlImage(), plate.getUrlImage()),
                ()->assertEquals(createPlateRequestDto.getRestaurantId(), plate.getRestaurantId()),
                ()->assertTrue(plate.getIsActive())
        );
    }
}