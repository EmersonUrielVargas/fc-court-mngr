package com.foodcourt.court.domain.usecase;

import com.foodcourt.court.domain.DataDomainFactory;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.OrderStatus;
import com.foodcourt.court.domain.exception.DomainException;
import com.foodcourt.court.domain.exception.OrderIsEmptyException;
import com.foodcourt.court.domain.exception.PlateNotFoundException;
import com.foodcourt.court.domain.model.Order;
import com.foodcourt.court.domain.spi.*;
import com.foodcourt.court.shared.DataConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @Mock
    private IPlatePersistencePort platePersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @Mock
    private IAuthenticationPort authenticationPort;

    @InjectMocks
    private OrderUseCases orderUseCases;

    @Nested
    @DisplayName("createOrder")
    class createOrderTests{
        @Test
        void createOrderSuccessful() {
            Order newOrder = DataDomainFactory.createOrder();
            Long authenticatedUserId = DataConstants.DEFAULT_USER_ID;
            List<String> activeStatuses = List.of(
                    OrderStatus.PENDING.getMessage(),
                    OrderStatus.IN_PREPARATION.getMessage(),
                    OrderStatus.PREPARED.getMessage()
            );
            List<Long> plateIdsInOrder = List.of(101L);

            when(authenticationPort.getAuthenticateUserId()).thenReturn(authenticatedUserId);
            when(orderPersistencePort.hasActiveOrdersByClientId(authenticatedUserId, activeStatuses)).thenReturn(false);
            when(restaurantPersistencePort.getById(newOrder.getRestaurantId())).thenReturn(Optional.of(DataDomainFactory.createRestaurant()));
            when(platePersistencePort.findExistingPlateIdsInRestaurant(eq(newOrder.getRestaurantId()), anyList())).thenReturn(plateIdsInOrder);


            orderUseCases.create(newOrder);

            verify(authenticationPort).getAuthenticateUserId();
            verify(orderPersistencePort).hasActiveOrdersByClientId(authenticatedUserId, activeStatuses);
            verify(orderPersistencePort).upsertOrder(newOrder);
        }

        @Test
        void createOrderFail_OrderPlatesEmpty() {
            Order newOrder = DataDomainFactory.createOrder();
            newOrder.setOrderPlates(List.of());

            assertThrows(OrderIsEmptyException.class, () -> {
                orderUseCases.create(newOrder);
            });
        }

        @Test
        void createOrderFail_ClientWithActiveOrders() {
            Order newOrder = DataDomainFactory.createOrder();
            Long authenticatedUserId = DataConstants.DEFAULT_USER_ID;
            List<String> activeStatuses = List.of(
                    OrderStatus.PENDING.getMessage(),
                    OrderStatus.IN_PREPARATION.getMessage(),
                    OrderStatus.PREPARED.getMessage()
            );

            when(authenticationPort.getAuthenticateUserId()).thenReturn(authenticatedUserId);
            when(orderPersistencePort.hasActiveOrdersByClientId(authenticatedUserId, activeStatuses)).thenReturn(true);

            DomainException exception = assertThrows(DomainException.class, () -> {
                orderUseCases.create(newOrder);
            });

            assertEquals(Constants.CLIENT_HAS_ORDERS_ACTIVE, exception.getMessage());
        }

        @Test
        void createOrderFail_PlatesInOrderNotFound() {
            Order newOrder = DataDomainFactory.createOrder();
            Long authenticatedUserId = DataConstants.DEFAULT_USER_ID;
            List<String> activeStatuses = List.of(
                    OrderStatus.PENDING.getMessage(),
                    OrderStatus.IN_PREPARATION.getMessage(),
                    OrderStatus.PREPARED.getMessage()
            );
            List<Long> plateIdsInOrder = List.of(101L, 102L);

            when(authenticationPort.getAuthenticateUserId()).thenReturn(authenticatedUserId);
            when(orderPersistencePort.hasActiveOrdersByClientId(authenticatedUserId, activeStatuses)).thenReturn(false);
            when(restaurantPersistencePort.getById(newOrder.getRestaurantId())).thenReturn(Optional.of(DataDomainFactory.createRestaurant()));
            when(platePersistencePort.findExistingPlateIdsInRestaurant(eq(newOrder.getRestaurantId()), anyList())).thenReturn(plateIdsInOrder);

            assertThrows(PlateNotFoundException.class, () -> {
                orderUseCases.create(newOrder);
            });
        }
    }


}