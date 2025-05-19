package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.model.Restaurant;

public interface IRestaurantPersistencePort {
    void createRestaurant(Restaurant restaurant);
}
