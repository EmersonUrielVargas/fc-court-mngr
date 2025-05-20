package com.foodcourt.court.infrastructure.out.jpa.adapter;

import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;


    @Override
    public void createRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurantEntityMapper.toRestaurantEntity(restaurant));
    }

    @Override
    public Optional<Restaurant> getById(Long restaurantID) {
        return restaurantRepository.findById(restaurantID).map(restaurantEntityMapper::toRestaurant);
    }
}
