package com.foodcourt.court.domain.usecase;

import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.domain.exception.DomainException;
import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import com.foodcourt.court.domain.spi.IUserVerificationPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Restaurant restaurant = Restaurant.builder()
                .name("El gran Sabor")
                .nit("1235622434")
                .address("calle 22 sur 34-11")
                .phoneNumber("+573158000111")
                .ownerId(10L)
                .urlLogo("www.logo.com")
                .build();
        UserRole roleOwner = UserRole.OWNER;
        Long userId = 10L;

        when(userVerificationPort.getRolUser(userId)).thenReturn(Optional.of(roleOwner));
        restaurantUseCase.create(restaurant);

        verify(restaurantPersistencePort).createRestaurant(restaurant);
    }

    @Test
    void createRestaurantFailUserRoleNotFound() {
        Restaurant restaurant = Restaurant.builder()
                .name("El gran Sabor")
                .nit("1235622434")
                .address("calle 22 sur 34-11")
                .phoneNumber("+573158000111")
                .ownerId(10L)
                .urlLogo("www.logo.com")
                .build();
        Long userId = 10L;

        when(userVerificationPort.getRolUser(userId)).thenReturn(Optional.empty());
        DomainException exception = assertThrows(DomainException.class, ()-> restaurantUseCase.create(restaurant));
        assertEquals(Constants.OWNER_NO_FOUND, exception.getMessage());
    }
}