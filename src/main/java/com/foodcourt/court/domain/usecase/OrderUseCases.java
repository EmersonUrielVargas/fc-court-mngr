package com.foodcourt.court.domain.usecase;

import com.foodcourt.court.domain.api.IOrderServicePort;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.OrderStatus;
import com.foodcourt.court.domain.exception.*;
import com.foodcourt.court.domain.model.*;
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
    private final ITrackingOrderPersistencePort trackingOrderPersistencePort;



    public OrderUseCases(IPlatePersistencePort platePersistencePort,
                         IOrderPersistencePort orderPersistencePort,
                         IAuthenticationPort authenticationPort,
                         IRestaurantPersistencePort restaurantPersistencePort,
                         IAssignmentEmployeePort assignmentEmployeePort,
                         INotificationPort notificationPort,
                         IUserVerificationPort userVerificationPort,
                         ITrackingOrderPersistencePort trackingOrderPersistencePort) {
        this.platePersistencePort = platePersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.authenticationPort = authenticationPort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.assignmentEmployeePort = assignmentEmployeePort;
        this.notificationPort = notificationPort;
        this.userVerificationPort = userVerificationPort;
        this.trackingOrderPersistencePort = trackingOrderPersistencePort;
    }


    @Override
    public void create(Order newOrder) {
        UtilitiesValidator.validateIsNull(newOrder.getRestaurantId());
        UtilitiesValidator.validateIsNull(newOrder.getOrderPlates());
        if (newOrder.getOrderPlates().isEmpty()){
            throw new OrderIsEmptyException();
        }
        Long userIdAuthenticated =  authenticationPort.getAuthenticateUserId();
        String userEmailAuthenticated =  authenticationPort.getAuthenticateUserEmail();
        newOrder.setClientId(userIdAuthenticated);
        List<String> statusActive = List.of(
                OrderStatus.PENDING.name(),
                OrderStatus.IN_PREPARATION.name(),
                OrderStatus.PREPARED.name());
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
        Order orderSaved = orderPersistencePort.upsertOrder(newOrder);
        TrackingOrder trackingOrder =  generateTrackingOrder(orderSaved, null, userEmailAuthenticated, null);
        trackingOrderPersistencePort.createTrackingOrder(trackingOrder);
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
        OrderStatus previousStatus =  orderFound.getStatus();
        Long employeeIdAuth =  authenticationPort.getAuthenticateUserId();
        String employeeEmailAuth =  authenticationPort.getAuthenticateUserEmail();
        User clientInfo = userVerificationPort.getUserInfo(orderFound.getClientId())
                .orElseThrow(UserNotFoundException::new);
        if (assignmentEmployeePort.getAssignment(orderFound.getRestaurantId(), employeeIdAuth).isEmpty()){
            throw new ActionNotAllowedException(Constants.EMPLOYEE_NOT_ALLOWED);
        }
        switch (orderFound.getStatus()) {
            case PENDING -> assignOrder(orderFound, employeeIdAuth);
            case IN_PREPARATION -> notifyReadyOrder(orderFound, clientInfo);
            case PREPARED -> finalizeOrder(orderFound, clientCode);
            default -> throw new ActionNotAllowedException(Constants.ORDER_STATUS_ACTION_NOT_ALLOWED);
        }

        Order orderSaved = orderPersistencePort.upsertOrder(orderFound);
        TrackingOrder trackingOrder =  generateTrackingOrder(orderSaved, previousStatus, clientInfo.getEmail(), employeeEmailAuth);
        trackingOrderPersistencePort.createTrackingOrder(trackingOrder);
    }

    @Override
    public void cancelOrder(Long idOrder) {
        UtilitiesValidator.validateIsNull(idOrder);
        Order orderFound =  orderPersistencePort.findById(idOrder)
                .orElseThrow(OrderNotFoundException::new);
        OrderStatus previousStatus =  orderFound.getStatus();
        Long clientIdAuth =  authenticationPort.getAuthenticateUserId();
        String clientEmailAuth =  authenticationPort.getAuthenticateUserEmail();
        if (!orderFound.getClientId().equals(clientIdAuth)){
            throw new ActionNotAllowedException(Constants.CLIENT_NOT_ALLOWED);
        }
        if (orderFound.getStatus() == OrderStatus.CANCELED){
            throw new ActionNotAllowedException(Constants.ORDER_STATUS_ACTION_NOT_ALLOWED);
        }
        UtilitiesValidator.validateCorrectOrderStatus(orderFound.getStatus(), OrderStatus.PENDING, Constants.ORDER_STATUS_NOT_ALLOWED_MESSAGE_CLIENT);
        orderFound.setStatus(OrderStatus.CANCELED);
        Order orderSaved = orderPersistencePort.upsertOrder(orderFound);
        TrackingOrder trackingOrder =  generateTrackingOrder(orderSaved, previousStatus, clientEmailAuth, null);
        trackingOrderPersistencePort.createTrackingOrder(trackingOrder);
    }

    @Override
    public List<Long> getOrdersIdByOwnerId(Long ownerId) {
        UtilitiesValidator.validateIsNull(ownerId);
        Restaurant ownerRestaurant = restaurantPersistencePort.getByOwnerId(ownerId)
                .orElseThrow(()->new RestaurantNotFoundException(Constants.RESTAURANT_OWNER_NO_FOUND));
        return orderPersistencePort.getOrdersIdByRestaurantId(ownerRestaurant.getId());
    }

    private void assignOrder(Order order, Long chefId){
        UtilitiesValidator.validateCorrectOrderStatus(order.getStatus(), OrderStatus.PENDING, null);
        order.setStatus(OrderStatus.IN_PREPARATION);
        order.setChefId(chefId);
    }

    private void notifyReadyOrder(Order order, User client){
        UtilitiesValidator.validateCorrectOrderStatus(order.getStatus(), OrderStatus.IN_PREPARATION, null);
        order.setStatus(OrderStatus.PREPARED);
        String pinCode = Utilities.generateRandomPin();
        String messageNotify = generateCodeMessage(client.getName(), order.getId(), pinCode);
        notificationPort.sendTextMessage(messageNotify, client.getPhoneNumber());
        order.setCodeValidation(pinCode);
    }

    private void finalizeOrder(Order order, String pinCode){
        UtilitiesValidator.validateCorrectOrderStatus(order.getStatus(), OrderStatus.PREPARED, null);
        UtilitiesValidator.validateIsNull(pinCode);
        UtilitiesValidator.validateStringPattern(pinCode, Constants.ID_NUMBER_PATTERN, Constants.CLIENT_PIN_CODE_INCORRECT);
        if (!Objects.equals(pinCode, order.getCodeValidation())){
            throw new NotAllowedValueException(Constants.CLIENT_PIN_CODE_INCORRECT);
        }
        order.setStatus(OrderStatus.DELIVERED);
    }

    private String generateCodeMessage(String clientName, Long orderId, String pinCode){
        return String.format(Constants.MESSAGE_PIN_CODE_CLIENT_TEMPLATE, clientName, orderId, pinCode);
    }

    private TrackingOrder generateTrackingOrder(Order order, OrderStatus previousStatus,String clientEmail, String employeeEmail){
        return TrackingOrder.builder()
                .orderId(order.getId())
                .date(LocalDateTime.now())
                .employeeId(order.getChefId())
                .employeeEmail(employeeEmail)
                .clientEmail(clientEmail)
                .clientId(order.getClientId())
                .nextStatus(order.getStatus().getMessage())
                .previousStatus(Objects.nonNull(previousStatus)? previousStatus.getMessage(): null)
                .build();
    }
}
