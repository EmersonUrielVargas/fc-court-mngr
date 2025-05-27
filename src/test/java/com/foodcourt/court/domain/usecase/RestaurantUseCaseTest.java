package com.foodcourt.court.domain.usecase;

import com.foodcourt.court.domain.DataDomainFactory;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.domain.exception.DomainException;
import com.foodcourt.court.domain.exception.NotAllowedValueException;
import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import com.foodcourt.court.domain.spi.IUserVerificationPort;
import com.foodcourt.court.shared.DataConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IUserVerificationPort userVerificationPort;
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    @Test
    void createRestaurant() {
        Restaurant restaurant = DataDomainFactory.createRestaurant();
        UserRole roleOwner = UserRole.OWNER;

        when(userVerificationPort.getRolUser(DataConstants.DEFAULT_OWNER_ID)).thenReturn(Optional.of(roleOwner));
        restaurantUseCase.create(restaurant);

        verify(restaurantPersistencePort).upsertRestaurant(restaurant);
    }

    @Test
    void createRestaurantFailUserRoleNotFound() {
        Restaurant restaurant = DataDomainFactory.createRestaurant();

        when(userVerificationPort.getRolUser(DataConstants.DEFAULT_OWNER_ID)).thenReturn(Optional.empty());
        DomainException exception = assertThrows(DomainException.class, ()-> restaurantUseCase.create(restaurant));
        assertEquals(Constants.OWNER_NO_FOUND, exception.getMessage());
    }

    @Test
    void getRestaurantsSuccessful() {
        Restaurant restaurant = DataDomainFactory.createRestaurant();
        List<Restaurant> restaurants = List.of(restaurant,restaurant);

        when(restaurantPersistencePort.getRestaurants(DataConstants.DEFAULT_PAGE_SIZE, DataConstants.DEFAULT_PAGE)).thenReturn(restaurants);
        List<Restaurant> restaurantsFound = restaurantUseCase.getRestaurants(DataConstants.DEFAULT_PAGE_SIZE, DataConstants.DEFAULT_PAGE);

        verify(restaurantPersistencePort).getRestaurants(DataConstants.DEFAULT_PAGE_SIZE, DataConstants.DEFAULT_PAGE);
        assertEquals(restaurantsFound.size(), restaurants.size());
        assertTrue(restaurantsFound.size() <= DataConstants.DEFAULT_PAGE_SIZE);
    }

    @Test
    void getRestaurantsFailNegativeValue() {
        Integer negativeValue = -12;

        NotAllowedValueException exception = assertThrows(NotAllowedValueException.class, ()-> restaurantUseCase.getRestaurants(negativeValue, DataConstants.DEFAULT_PAGE));
        assertEquals(String.format(Constants.NEGATIVE_VALUE_PARAM_TEMPLATE, Constants.PAGE_SIZE_NAME), exception.getMessage());
    }
}