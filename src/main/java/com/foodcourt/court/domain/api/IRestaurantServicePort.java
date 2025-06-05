package com.foodcourt.court.domain.api;

import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.utilities.CustomPage;

import java.util.List;

public interface IRestaurantServicePort {
    void create(Restaurant restaurant);
    CustomPage<Restaurant> getRestaurants(Integer pageSize, Integer page);
    void assignEmployee(Long employeeId);
    List<Long> getEmployeesIdByOwnerId(Long ownerId);
}
