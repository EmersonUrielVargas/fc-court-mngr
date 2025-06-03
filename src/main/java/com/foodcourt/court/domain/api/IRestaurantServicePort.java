package com.foodcourt.court.domain.api;

import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.utilities.CustomPage;

public interface IRestaurantServicePort {
    void create(Restaurant restaurant);
    CustomPage<Restaurant> getRestaurants(Integer pageSize, Integer page);
    void assignEmployee(Long employeeId);
}
