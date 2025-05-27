package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface IRestaurantPersistencePort {
    void upsertRestaurant(Restaurant restaurant);

    Optional<Restaurant> getById(Long restaurantID);

    List<Restaurant> getRestaurants(Integer pageSize,Integer page);
}
