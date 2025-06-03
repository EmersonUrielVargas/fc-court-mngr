package com.foodcourt.court.domain.usecase;

import com.foodcourt.court.domain.DataDomainFactory;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.domain.exception.DomainException;
import com.foodcourt.court.domain.exception.NotAllowedValueException;
import com.foodcourt.court.domain.exception.RestaurantNotFoundException;
import com.foodcourt.court.domain.model.AssignmentEmployee;
import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.spi.IAssignmentEmployeePort;
import com.foodcourt.court.domain.spi.IAuthenticationPort;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import com.foodcourt.court.domain.spi.IUserVerificationPort;
import com.foodcourt.court.domain.utilities.CustomPage;
import com.foodcourt.court.shared.DataConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IUserVerificationPort userVerificationPort;
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private IAssignmentEmployeePort assignmentEmployeePort;
    @Mock
    private IAuthenticationPort authenticationPort;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    @Nested
    @DisplayName("createRestaurant")
    class createRestaurantTests{
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
    }

    @Nested
    @DisplayName("getRestaurants")
    class getRestaurantsTests{

        @Test
        void getRestaurantsSuccessful() {
            Restaurant restaurant = DataDomainFactory.createRestaurant();
            List<Restaurant> restaurants = List.of(restaurant,restaurant);
            CustomPage<Restaurant> pageRestaurants = DataDomainFactory.createCustomPage(restaurants);

            when(restaurantPersistencePort.getRestaurants(DataConstants.DEFAULT_PAGE_SIZE, DataConstants.DEFAULT_PAGE)).thenReturn(pageRestaurants);
            CustomPage<Restaurant> restaurantsFound = restaurantUseCase.getRestaurants(DataConstants.DEFAULT_PAGE_SIZE, DataConstants.DEFAULT_PAGE);

            verify(restaurantPersistencePort).getRestaurants(DataConstants.DEFAULT_PAGE_SIZE, DataConstants.DEFAULT_PAGE);
            assertEquals(restaurantsFound.getData().size(), restaurants.size());
            assertTrue(restaurantsFound.getData().size() <= DataConstants.DEFAULT_PAGE_SIZE);
        }

        @Test
        void getRestaurantsFailNegativeValue() {
            Integer negativeValue = -12;

            NotAllowedValueException exception = assertThrows(NotAllowedValueException.class, ()-> restaurantUseCase.getRestaurants(negativeValue, DataConstants.DEFAULT_PAGE));
            assertEquals(String.format(Constants.NEGATIVE_VALUE_PARAM_TEMPLATE, Constants.PAGE_SIZE_NAME), exception.getMessage());
        }
    }

    @Nested
    @DisplayName("assignEmployee")
    class assignEmployeeTests{

        @Test
        void assignEmployee_ownerHasRestaurant_shouldCreateAssignment() {
            Long employeeIdToAssign = DataConstants.DEFAULT_USER_ID;
            Long authenticatedOwnerId = DataConstants.DEFAULT_OWNER_ID;
            Long restaurantId = DataConstants.DEFAULT_RESTAURANT_ID;

            Restaurant ownerRestaurant = DataDomainFactory.createRestaurant();

            when(authenticationPort.getAuthenticateUserId()).thenReturn(authenticatedOwnerId);
            when(restaurantPersistencePort.getByOwnerId(authenticatedOwnerId)).thenReturn(Optional.of(ownerRestaurant));

            restaurantUseCase.assignEmployee(employeeIdToAssign);

            verify(authenticationPort, times(1)).getAuthenticateUserId();
            verify(restaurantPersistencePort, times(1)).getByOwnerId(authenticatedOwnerId);

            ArgumentCaptor<AssignmentEmployee> assignmentCaptor = ArgumentCaptor.forClass(AssignmentEmployee.class);
            verify(assignmentEmployeePort, times(1)).createAssignment(assignmentCaptor.capture());

            AssignmentEmployee capturedAssignment = assignmentCaptor.getValue();
            assertNotNull(capturedAssignment);
            assertEquals(restaurantId, capturedAssignment.getRestaurantId());
            assertEquals(employeeIdToAssign, capturedAssignment.getEmployeeId());
        }

        @Test
        void assignEmployee_ownerHasNoRestaurant_shouldThrowRestaurantNotFoundException() {
            Long employeeIdToAssign = DataConstants.DEFAULT_USER_ID;
            Long authenticatedOwnerId = DataConstants.DEFAULT_USER_ID;

            when(authenticationPort.getAuthenticateUserId()).thenReturn(authenticatedOwnerId);
            when(restaurantPersistencePort.getByOwnerId(authenticatedOwnerId)).thenReturn(Optional.empty());

            RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
                restaurantUseCase.assignEmployee(employeeIdToAssign);
            });

            assertEquals(Constants.RESTAURANT_OWNER_NO_FOUND, exception.getMessage());

            verify(authenticationPort, times(1)).getAuthenticateUserId();
            verify(restaurantPersistencePort, times(1)).getByOwnerId(authenticatedOwnerId);
            verify(assignmentEmployeePort, never()).createAssignment(any(AssignmentEmployee.class));
        }
    }
}