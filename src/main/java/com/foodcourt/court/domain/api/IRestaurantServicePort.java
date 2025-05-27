package com.foodcourt.court.domain.api;

import com.foodcourt.court.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantServicePort {
    void create(Restaurant restaurant);
    List<Restaurant> getRestaurants(Integer pageSize,Integer page);
}
