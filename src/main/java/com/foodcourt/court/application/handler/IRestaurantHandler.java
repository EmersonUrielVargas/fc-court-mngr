package com.foodcourt.court.application.handler;

import com.foodcourt.court.application.dto.request.AssignEmployeeRequestDto;
import com.foodcourt.court.application.dto.request.RestaurantRequestDto;
import com.foodcourt.court.application.dto.response.ListRestaurantsResponseDto;

import java.util.List;

public interface IRestaurantHandler {

    void create(RestaurantRequestDto restaurantRq);

    List<ListRestaurantsResponseDto> getRestaurants(Integer pageSize, Integer page);

    void assignEmployee(AssignEmployeeRequestDto employeeData);

}
