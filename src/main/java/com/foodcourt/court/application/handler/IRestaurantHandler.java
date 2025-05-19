package com.foodcourt.court.application.handler;

import com.foodcourt.court.application.dto.request.RestaurantRequestDto;

public interface IRestaurantHandler {

    void create(RestaurantRequestDto restaurantRq);
}
