package com.foodcourt.court.domain.usecase;

import com.foodcourt.court.domain.api.IOrderServicePort;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.OrderStatus;
import com.foodcourt.court.domain.exception.*;
import com.foodcourt.court.domain.model.Order;
import com.foodcourt.court.domain.model.OrderPlate;
import com.foodcourt.court.domain.model.User;
import com.foodcourt.court.domain.spi.*;
import com.foodcourt.court.domain.utilities.CustomPage;
import com.foodcourt.court.domain.utilities.Utilities;
import com.foodcourt.court.domain.validators.UtilitiesValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderUseCases implements IOrderServicePort {

    private final IPlatePersistencePort platePersistencePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IAuthenticationPort authenticationPort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IAssignmentEmployeePort assignmentEmployeePort;
    private final INotificationPort notificationPort;
    private final IUserVerificationPort userVerificationPort;



    public OrderUseCases(IPlatePersistencePort platePersistencePort,
                         IOrderPersistencePort orderPersistencePort,
                         IAuthenticationPort authenticationPort,
                         IRestaurantPersistencePort restaurantPersistencePort,
                         IAssignmentEmployeePort assignmentEmployeePort,
                         INotificationPort notificationPort,
                         IUserVerificationPort userVerificationPort) {
        this.platePersistencePort = platePersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.authenticationPort = authenticationPort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.assignmentEmployeePort = assignmentEmployeePort;
        this.notificationPort = notificationPort;
        this.userVerificationPort = userVerificationPort;
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
            throw new ActionNotAllowedException(Constants.CLIENT_HAS_ORDERS_ACTIVE);
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

    @Override
    public CustomPage<Order> getOrdersByStatus(Long restaurantId, Integer pageSize, Integer page, String status) {
        UtilitiesValidator.validateIsNull(restaurantId);
        UtilitiesValidator.validateIsNull(pageSize);
        UtilitiesValidator.validateIsNull(page);
        UtilitiesValidator.validateNotNegativeNumber(pageSize, Constants.PAGE_SIZE_NAME);
        UtilitiesValidator.validateNotNegativeNumber(page, Constants.PAGE_NAME);
        UtilitiesValidator.getOrderStatus(status);
        restaurantPersistencePort.getById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);
        Long userIdAuthenticated =  authenticationPort.getAuthenticateUserId();
        if (assignmentEmployeePort.getAssignment(restaurantId, userIdAuthenticated).isEmpty()){
            throw new ActionNotAllowedException(Constants.EMPLOYEE_NOT_ALLOWED);
        }
        return orderPersistencePort.getOrdersByStatus(restaurantId, pageSize, page, status);
    }

    @Override
    public void updateStatusOrder(Long idOrder, String status, String clientCode) {
        UtilitiesValidator.validateIsNull(idOrder);
        UtilitiesValidator.getOrderStatus(status);
        Order orderFound =  orderPersistencePort.findById(idOrder)
                .orElseThrow(OrderNotFoundException::new);
        Long userIdAuthenticated =  authenticationPort.getAuthenticateUserId();
        if (assignmentEmployeePort.getAssignment(orderFound.getRestaurantId(), userIdAuthenticated).isEmpty()){
            throw new ActionNotAllowedException(Constants.EMPLOYEE_NOT_ALLOWED);
        }
        orderFound = switch (orderFound.getStatus()) {
            case PENDING -> assignOrder(orderFound, userIdAuthenticated);
            case IN_PREPARATION -> notifyReadyOrder(orderFound);
            case PREPARED -> finalizeOrder(orderFound, clientCode);
            default -> throw new ActionNotAllowedException(Constants.ORDER_STATUS_ACTION_NOT_ALLOWED);
        };
        orderPersistencePort.upsertOrder(orderFound);
    }

    private Order assignOrder(Order order, Long chefId){
        UtilitiesValidator.validateCorrectOrderStatus(order.getStatus(), OrderStatus.PENDING);
        order.setStatus(OrderStatus.IN_PREPARATION);
        order.setChefId(chefId);
        return order;
    }

    private Order notifyReadyOrder(Order order){
        UtilitiesValidator.validateCorrectOrderStatus(order.getStatus(), OrderStatus.IN_PREPARATION);
        order.setStatus(OrderStatus.PREPARED);
        String pinCode = Utilities.generateRandomPin();
        User user = userVerificationPort.getUserInfo(order.getClientId())
                .orElseThrow(UserNotFoundException::new);
        String messageNotify = generateCodeMessage(user.getName(), order.getId(), pinCode);
        notificationPort.sendTextMessage(messageNotify, user.getPhoneNumber());
        order.setCodeValidation(pinCode);
        return order;
    }

    private Order finalizeOrder(Order order, String pinCode){
        UtilitiesValidator.validateCorrectOrderStatus(order.getStatus(), OrderStatus.PREPARED);
        UtilitiesValidator.validateIsNull(pinCode);
        UtilitiesValidator.validateStringPattern(pinCode, Constants.ID_NUMBER_PATTERN, Constants.CLIENT_PIN_CODE_INCORRECT);
        if (!Objects.equals(pinCode, order.getCodeValidation())){
            throw new NotAllowedValueException(Constants.CLIENT_PIN_CODE_INCORRECT);
        }
        order.setStatus(OrderStatus.DELIVERED);
        return order;
    }

    private String generateCodeMessage(String clientName, Long orderId, String pinCode){
        return String.format(Constants.MESSAGE_PIN_CODE_CLIENT_TEMPLATE, clientName, orderId, pinCode);
    }
}
