package com.foodcourt.court.domain.api;

import com.foodcourt.court.domain.model.Restaurant;

public interface IRestaurantServicePort {
    void create(Restaurant restaurant);
}
