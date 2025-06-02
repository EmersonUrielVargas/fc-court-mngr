package com.foodcourt.court.application.handler.impl;

import com.foodcourt.court.application.dto.request.AssignEmployeeRequestDto;
import com.foodcourt.court.application.dto.request.RestaurantRequestDto;
import com.foodcourt.court.application.dto.response.ListRestaurantsResponseDto;
import com.foodcourt.court.application.handler.IRestaurantHandler;
import com.foodcourt.court.application.mapper.IRestaurantRequestMapper;
import com.foodcourt.court.domain.api.IRestaurantServicePort;
import com.foodcourt.court.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<ListRestaurantsResponseDto> getRestaurants(Integer pageSize, Integer page) {
        return restaurantServicePort.getRestaurants(pageSize, page)
                .stream().map(restaurantRequestMapper::toListRestaurantsResponseDto)
                .toList();
    }

    @Override
    public void assignEmployee(AssignEmployeeRequestDto employeeData) {
        restaurantServicePort.assignEmployee(employeeData.getIdEmployee());
    }
}
