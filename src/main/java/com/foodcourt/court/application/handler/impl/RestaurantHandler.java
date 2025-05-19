package com.foodcourt.court.application.handler.impl;

import com.foodcourt.court.application.dto.request.RestaurantRequestDto;
import com.foodcourt.court.application.handler.IRestaurantHandler;
import com.foodcourt.court.application.mapper.IRestaurantRequestMapper;
import com.foodcourt.court.domain.api.IRestaurantServicePort;
import com.foodcourt.court.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantHandler implements IRestaurantHandler {
    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;

    @Override
    public void create(RestaurantRequestDto restaurantRq) {
        Restaurant restaurant = restaurantRequestMapper.toRestaurant(restaurantRq);
        restaurantServicePort.create(restaurant);
    }
}
