package com.foodcourt.court.domain.usecase;

import com.foodcourt.court.domain.api.IRestaurantServicePort;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.domain.exception.DomainException;
import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import com.foodcourt.court.domain.spi.IUserVerificationPort;
import com.foodcourt.court.domain.validators.RestaurantValidator;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IUserVerificationPort userVerificationPort;
    private final IRestaurantPersistencePort restaurantPersistencePort;


    public RestaurantUseCase(IUserVerificationPort userVerificationPort,
                             IRestaurantPersistencePort restaurantPersistencePort) {
        this.userVerificationPort = userVerificationPort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }


    @Override
    public void create(Restaurant restaurant) {

        RestaurantValidator.validateStringPattern(restaurant.getName(),Constants.NAME_RESTAURANT_PATTERN, Constants.INVALID_NAME);
        RestaurantValidator.validateStringPattern(restaurant.getPhoneNumber(), Constants.PHONE_NUMBER_PATTERN, Constants.INVALID_PHONE_NUMBER);
        RestaurantValidator.validateStringPattern(restaurant.getNit(), Constants.ID_NUMBER_PATTERN, Constants.INVALID_ID_NUMBER);
        UserRole roleUser = userVerificationPort.getRolUser(restaurant.getOwnerId())
                .orElseThrow(()-> new DomainException(Constants.OWNER_NO_FOUND));
        RestaurantValidator.validateOwner(roleUser);
        restaurantPersistencePort.createRestaurant(restaurant);
    }
}
