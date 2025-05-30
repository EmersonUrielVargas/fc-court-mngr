package com.foodcourt.court.domain.usecase;

import com.foodcourt.court.domain.api.IOrderServicePort;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.OrderStatus;
import com.foodcourt.court.domain.exception.*;
import com.foodcourt.court.domain.model.Order;
import com.foodcourt.court.domain.model.OrderPlate;
import com.foodcourt.court.domain.spi.*;
import com.foodcourt.court.domain.validators.UtilitiesValidator;

import java.time.LocalDateTime;
import java.util.List;

public class OrderUseCases implements IOrderServicePort {

    private final IPlatePersistencePort platePersistencePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IAuthenticationPort authenticationPort;
    private final IRestaurantPersistencePort restaurantPersistencePort;


    public OrderUseCases(IPlatePersistencePort platePersistencePort,
                         IOrderPersistencePort orderPersistencePort,
                         IAuthenticationPort authenticationPort,
                         IRestaurantPersistencePort restaurantPersistencePort) {
        this.platePersistencePort = platePersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.authenticationPort = authenticationPort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }


    @Override
    public void create(Order newOrder) {
        UtilitiesValidator.validateIsNull(newOrder.getRestaurantId());
        UtilitiesValidator.validateIsNull(newOrder.getOrderPlates());
        if (newOrder.getOrderPlates().isEmpty()){
            throw  new OrderIsEmptyException();
        }
        Long userIdAuthenticated =  authenticationPort.getAuthenticateUserId();
        newOrder.setClientId(userIdAuthenticated);
        List<String> statusActive = List.of(
                OrderStatus.PENDING.getMessage(),
                OrderStatus.IN_PREPARATION.getMessage(),
                OrderStatus.PREPARED.getMessage());
        if (orderPersistencePort.hasActiveOrdersByClientId(userIdAuthenticated, statusActive).booleanValue()){
            throw new DomainException(Constants.CLIENT_HAS_ORDERS_ACTIVE);
        }
        restaurantPersistencePort.getById(newOrder.getRestaurantId())
                .orElseThrow(RestaurantNotFoundException::new);
        List<Long> idPlates = newOrder.getOrderPlates().stream()
                .map(OrderPlate::getPlateId)
                .distinct()
                .toList();
        List<Long> idPlatesValid = platePersistencePort.findExistingPlateIdsInRestaurant(newOrder.getRestaurantId(), idPlates);
        if (idPlates.size() != idPlatesValid.size()){
            throw new PlateNotFoundException(Constants.PLATE_NO_FOUND_IN_ORDER);
        }
        newOrder.setDate(LocalDateTime.now());
        orderPersistencePort.upsertOrder(newOrder);
    }
}
