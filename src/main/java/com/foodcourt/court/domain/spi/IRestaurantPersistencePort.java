package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.model.Restaurant;

import java.util.Optional;

public interface IRestaurantPersistencePort {
    void createRestaurant(Restaurant restaurant);

    Optional<Restaurant> getById(Long restaurantID);
}
