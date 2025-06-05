package com.foodcourt.court.domain.usecase;

import com.foodcourt.court.domain.api.IRestaurantServicePort;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.domain.exception.RestaurantNotFoundException;
import com.foodcourt.court.domain.exception.UserNotFoundException;
import com.foodcourt.court.domain.model.AssignmentEmployee;
import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.spi.IAssignmentEmployeePort;
import com.foodcourt.court.domain.spi.IAuthenticationPort;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import com.foodcourt.court.domain.spi.IUserVerificationPort;
import com.foodcourt.court.domain.utilities.CustomPage;
import com.foodcourt.court.domain.validators.UtilitiesValidator;

import java.util.List;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IUserVerificationPort userVerificationPort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IAuthenticationPort authenticationPort;
    private final IAssignmentEmployeePort assignmentEmployeePort;


    public RestaurantUseCase(IUserVerificationPort userVerificationPort,
                             IRestaurantPersistencePort restaurantPersistencePort,
                             IAuthenticationPort authenticationPort, IAssignmentEmployeePort assignmentEmployeePort) {
        this.userVerificationPort = userVerificationPort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.authenticationPort = authenticationPort;
        this.assignmentEmployeePort = assignmentEmployeePort;
    }


    @Override
    public void create(Restaurant restaurant) {

        UtilitiesValidator.validateStringPattern(restaurant.getName(),Constants.NAME_RESTAURANT_PATTERN, Constants.INVALID_NAME);
        UtilitiesValidator.validateStringPattern(restaurant.getPhoneNumber(), Constants.PHONE_NUMBER_PATTERN, Constants.INVALID_PHONE_NUMBER);
        UtilitiesValidator.validateStringPattern(restaurant.getNit(), Constants.ID_NUMBER_PATTERN, Constants.INVALID_ID_NUMBER);
        UserRole roleUser = userVerificationPort.getRolUser(restaurant.getOwnerId())
                .orElseThrow(()-> new UserNotFoundException(Constants.OWNER_NO_FOUND));
        UtilitiesValidator.validateOwner(roleUser);
        restaurantPersistencePort.upsertRestaurant(restaurant);
    }

    @Override
    public CustomPage<Restaurant> getRestaurants(Integer pageSize, Integer page) {
        UtilitiesValidator.validateIsNull(pageSize);
        UtilitiesValidator.validateIsNull(page);
        UtilitiesValidator.validateNotNegativeNumber(pageSize, Constants.PAGE_SIZE_NAME);
        UtilitiesValidator.validateNotNegativeNumber(page, Constants.PAGE_NAME);
        return restaurantPersistencePort.getRestaurants(pageSize, page);
    }

    @Override
    public void assignEmployee(Long employeeId) {
        UtilitiesValidator.validateIsNull(employeeId);
        Long userIdAuthenticated =  authenticationPort.getAuthenticateUserId();
        Restaurant ownerRestaurant = restaurantPersistencePort.getByOwnerId(userIdAuthenticated)
                .orElseThrow(()->new RestaurantNotFoundException(Constants.RESTAURANT_OWNER_NO_FOUND));
        AssignmentEmployee assignment = AssignmentEmployee.builder()
                .restaurantId(ownerRestaurant.getId())
                .employeeId(employeeId)
                .build();
        assignmentEmployeePort.createAssignment(assignment);
    }

    @Override
    public List<Long> getEmployeesIdByOwnerId(Long ownerId) {
        UtilitiesValidator.validateIsNull(ownerId);
        Restaurant ownerRestaurant = restaurantPersistencePort.getByOwnerId(ownerId)
                .orElseThrow(()->new RestaurantNotFoundException(Constants.RESTAURANT_OWNER_NO_FOUND));
        return assignmentEmployeePort.getEmployeesIdByRestaurantId(ownerRestaurant.getId());
    }

}
