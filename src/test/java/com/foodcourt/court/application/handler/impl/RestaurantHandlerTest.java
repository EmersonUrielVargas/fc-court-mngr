package com.foodcourt.court.application.handler.impl;

import com.foodcourt.court.application.dto.request.RestaurantRequestDto;
import com.foodcourt.court.application.mapper.IRestaurantRequestMapper;
import com.foodcourt.court.domain.api.IRestaurantServicePort;
import com.foodcourt.court.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RestaurantHandlerTest {

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @Mock
    private IRestaurantRequestMapper restaurantRequestMapper;

    @InjectMocks
    private RestaurantHandler restaurantHandler;

    @Test
    void createRestaurantSuccessful() {
        RestaurantRequestDto restaurantRq =  new RestaurantRequestDto();

        Restaurant restaurant = new Restaurant();

        when(restaurantRequestMapper.toRestaurant(restaurantRq)).thenReturn(restaurant);
        restaurantHandler.create(restaurantRq);

        verify(restaurantRequestMapper, times(1)).toRestaurant(restaurantRq);
        verify(restaurantServicePort, times(1)).create(restaurant);
    }
}