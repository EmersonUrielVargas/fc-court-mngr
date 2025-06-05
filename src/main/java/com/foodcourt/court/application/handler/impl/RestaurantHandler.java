package com.foodcourt.court.application.handler.impl;

import com.foodcourt.court.application.dto.request.AssignEmployeeRequestDto;
import com.foodcourt.court.application.dto.request.RestaurantRequestDto;
import com.foodcourt.court.application.dto.response.RestaurantItemResponseDto;
import com.foodcourt.court.application.handler.IRestaurantHandler;
import com.foodcourt.court.application.mapper.IRestaurantRequestMapper;
import com.foodcourt.court.domain.api.IRestaurantServicePort;
import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.utilities.CustomPage;
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
    public CustomPage<RestaurantItemResponseDto> getRestaurants(Integer pageSize, Integer page) {
        CustomPage<Restaurant> pageRestaurants = restaurantServicePort.getRestaurants(pageSize, page);
        return new CustomPage<>(restaurantRequestMapper.toRestaurantsResponseDto(pageRestaurants.getData()), pageRestaurants);
    }

    @Override
    public void assignEmployee(AssignEmployeeRequestDto employeeData) {
        restaurantServicePort.assignEmployee(employeeData.getIdEmployee());
    }

    @Override
    public List<Long> getEmployeesIdByOwnerId(Long ownerId) {
        return restaurantServicePort.getEmployeesIdByOwnerId(ownerId);
    }
}
