package com.foodcourt.court.infrastructure.out.jpa.adapter;

import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.IRestaurantRepository;
import com.foodcourt.court.infrastructure.shared.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;


    @Override
    public void upsertRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurantEntityMapper.toRestaurantEntity(restaurant));
    }

    @Override
    public Optional<Restaurant> getById(Long restaurantID) {
        return restaurantRepository.findById(restaurantID).map(restaurantEntityMapper::toRestaurant);
    }

    @Override
    public List<Restaurant> getRestaurants(Integer pageSize, Integer page) {
        return restaurantRepository.findAll(PageRequest.of(page, pageSize, Sort.by(Constants.FIELD_NAME_GET_RESTAURANTS).ascending()))
                .map(restaurantEntityMapper::toRestaurant)
                .stream().toList();
    }
}
