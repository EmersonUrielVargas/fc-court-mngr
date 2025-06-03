package com.foodcourt.court.infrastructure.out.jpa.adapter;

import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import com.foodcourt.court.domain.utilities.CustomPage;
import com.foodcourt.court.infrastructure.out.jpa.entity.RestaurantEntity;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.IRestaurantRepository;
import com.foodcourt.court.infrastructure.shared.GeneralConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
    public CustomPage<Restaurant> getRestaurants(Integer pageSize, Integer page) {
        Page<RestaurantEntity> restaurantEntities = restaurantRepository.findAll(
                PageRequest.of(page, pageSize, Sort.by(GeneralConstants.FIELD_NAME).ascending())
        );
        CustomPage<Restaurant> restaurants = new CustomPage<>();
        restaurants.setData(restaurantEntityMapper.toRestaurant(restaurantEntities.getContent()));
        restaurants.setCurrentPage(restaurantEntities.getNumber());
        restaurants.setPageSize(restaurantEntities.getSize());
        restaurants.setTotalPages(restaurantEntities.getTotalPages());
        restaurants.setTotalItems(restaurantEntities.getTotalElements());
        restaurants.setIsLastPage(restaurantEntities.isLast());
        return restaurants;
    }

    @Override
    public Optional<Restaurant> getByOwnerId(Long ownerId) {
        return restaurantRepository.findByIdPropietario(ownerId).map(restaurantEntityMapper::toRestaurant);
    }
}
