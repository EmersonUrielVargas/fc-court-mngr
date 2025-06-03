package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.utilities.CustomPage;

import java.util.Optional;

public interface IRestaurantPersistencePort {
    void upsertRestaurant(Restaurant restaurant);

    Optional<Restaurant> getById(Long restaurantID);

    CustomPage<Restaurant> getRestaurants(Integer pageSize, Integer page);

    Optional<Restaurant> getByOwnerId(Long ownerId);
}
