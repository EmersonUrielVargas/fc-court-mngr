package com.foodcourt.court.application.handler;

import com.foodcourt.court.application.dto.request.AssignEmployeeRequestDto;
import com.foodcourt.court.application.dto.request.RestaurantRequestDto;
import com.foodcourt.court.application.dto.response.RestaurantItemResponseDto;
import com.foodcourt.court.domain.utilities.CustomPage;

import java.util.List;

public interface IRestaurantHandler {

    void create(RestaurantRequestDto restaurantRq);

    CustomPage<RestaurantItemResponseDto> getRestaurants(Integer pageSize, Integer page);

    void assignEmployee(AssignEmployeeRequestDto employeeData);

    List<Long> getEmployeesIdByOwnerId(Long ownerId);

}
