package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.model.Restaurant;

import java.util.Optional;

public interface IRestaurantPersistencePort {
    Optional<Restaurant> getByNit(String nitNumber);
    void createRestaurant(Restaurant restaurant);
}
