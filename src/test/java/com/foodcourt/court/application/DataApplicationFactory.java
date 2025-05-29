package com.foodcourt.court.application;

import com.foodcourt.court.application.dto.request.*;
import com.foodcourt.court.application.dto.response.ListRestaurantsResponseDto;
import com.foodcourt.court.shared.DataConstants;

import java.util.ArrayList;
import java.util.List;

public class DataApplicationFactory {

    public static ListRestaurantsResponseDto createListRestaurantDtoRs(){
        return ListRestaurantsResponseDto.builder()
                .name(DataConstants.DEFAULT_RESTAURANT_NAME)
                .urlLogo(DataConstants.DEFAULT_RESTAURANT_URL_LOGO)
                .build();
    }
    public static RestaurantRequestDto createRestaurantRequestDto(){
        RestaurantRequestDto restaurantRq =  new RestaurantRequestDto();
        restaurantRq.setName(DataConstants.DEFAULT_RESTAURANT_NAME);
        restaurantRq.setNit(DataConstants.DEFAULT_RESTAURANT_NIT);
        restaurantRq.setAddress(DataConstants.DEFAULT_RESTAURANT_ADDRESS);
        restaurantRq.setOwnerId(DataConstants.DEFAULT_OWNER_ID);
        restaurantRq.setPhoneNumber(DataConstants.DEFAULT_RESTAURANT_PHONE_NUMBER);
        restaurantRq.setUrlLogo(DataConstants.DEFAULT_RESTAURANT_URL_LOGO);
        return restaurantRq;
    }

    public static CreatePlateRequestDto createPlateRequestDto(){
        CreatePlateRequestDto createPlateRequestDto = new CreatePlateRequestDto();
        createPlateRequestDto.setName(DataConstants.DEFAULT_PLATE_NAME);
        createPlateRequestDto.setPrice(DataConstants.DEFAULT_PLATE_PRICE);
        createPlateRequestDto.setCategoryId(DataConstants.DEFAULT_CATEGORY_ID);
        createPlateRequestDto.setDescription(DataConstants.DEFAULT_PLATE_DESCRIPTION);
        createPlateRequestDto.setUrlImage(DataConstants.DEFAULT_PLATE_URL_IMAGE);
        createPlateRequestDto.setRestaurantId(DataConstants.DEFAULT_RESTAURANT_ID);
        return createPlateRequestDto;
    }

    public static UpdatePlateRequestDto createUpdatePlateRequestDto(){
        UpdatePlateRequestDto updatePlateRequestDto = new UpdatePlateRequestDto();
        updatePlateRequestDto.setId(DataConstants.DEFAULT_PLATE_ID);
        updatePlateRequestDto.setPrice(DataConstants.DEFAULT_PLATE_PRICE);
        updatePlateRequestDto.setDescription(DataConstants.DEFAULT_PLATE_DESCRIPTION);
        return updatePlateRequestDto;
    }

    public static StatusPlateRequestDto createStatusPlateRequestDto(){
        StatusPlateRequestDto statusPlateRequestDto = new StatusPlateRequestDto();
        statusPlateRequestDto.setId(DataConstants.DEFAULT_PLATE_ID);
        statusPlateRequestDto.setIsActive(DataConstants.DEFAULT_PLATE_IS_ACTIVE);
        return statusPlateRequestDto;
    }

    public static CreateOrderRequestDto createOrderRequestDto(){
        CreateOrderRequestDto orderRequestDto = new CreateOrderRequestDto();
        orderRequestDto.setRestaurantId(DataConstants.DEFAULT_RESTAURANT_ID);
        orderRequestDto.setOrderPlates(createListItemOrderRequestDto());
        return orderRequestDto;
    }

    public static CreateItemOrderRequestDto createItemOrderRequestDto(){
        CreateItemOrderRequestDto createItemOrderRequestDto = new CreateItemOrderRequestDto();
        createItemOrderRequestDto.setPlateId(DataConstants.DEFAULT_ORDER_PLATE_PLATE_ID);
        createItemOrderRequestDto.setQuantity(DataConstants.DEFAULT_QUANTITY);
        return createItemOrderRequestDto;
    }

    public static List<CreateItemOrderRequestDto> createListItemOrderRequestDto(){
        List<CreateItemOrderRequestDto> listItems = new ArrayList<>();
        listItems.add(createItemOrderRequestDto());
        return listItems;
    }


}
