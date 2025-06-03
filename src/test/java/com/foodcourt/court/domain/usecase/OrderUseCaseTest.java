package com.foodcourt.court.domain.usecase;

import com.foodcourt.court.domain.DataDomainFactory;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.OrderStatus;
import com.foodcourt.court.domain.exception.*;
import com.foodcourt.court.domain.model.Order;
import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.model.User;
import com.foodcourt.court.domain.spi.*;
import com.foodcourt.court.domain.utilities.CustomPage;
import com.foodcourt.court.domain.utilities.Utilities;
import com.foodcourt.court.shared.DataConstants;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

    @Mock
    private IAssignmentEmployeePort assignmentEmployeePort;

    @Mock
    private INotificationPort notificationPort;
    @Mock
    private IUserVerificationPort userVerificationPort;

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


    @Nested
    @DisplayName("getOrdersByStatus")
    class GetOrdersByStatusTests {

        @Test
        void getOrdersByStatus_Successful() {
            Long restaurantId = DataConstants.DEFAULT_RESTAURANT_ID;
            Integer pageSize = DataConstants.DEFAULT_PAGE_SIZE;
            Integer page = DataConstants.DEFAULT_PAGE;
            String status = OrderStatus.PENDING.getMessage();
            Long authenticatedUserId = DataConstants.DEFAULT_USER_ID;
            Restaurant mockRestaurant = DataDomainFactory.createRestaurant();
            List<Order> orders = List.of(DataDomainFactory.createOrder(), DataDomainFactory.createOrder());
            CustomPage<Order> expectedPage = DataDomainFactory.createCustomPage(orders);

            when(authenticationPort.getAuthenticateUserId()).thenReturn(authenticatedUserId);
            when(restaurantPersistencePort.getById(restaurantId)).thenReturn(Optional.of(mockRestaurant));
            when(assignmentEmployeePort.getAssignment(restaurantId, authenticatedUserId)).thenReturn(Optional.of(DataDomainFactory.createAssignmentEmployee()));
            when(orderPersistencePort.getOrdersByStatus(restaurantId, pageSize, page, status)).thenReturn(expectedPage);

            CustomPage<Order> actualPage = orderUseCases.getOrdersByStatus(restaurantId, pageSize, page, status);

            assertNotNull(actualPage);
            assertEquals(expectedPage, actualPage);
            verify(restaurantPersistencePort).getById(restaurantId);
            verify(authenticationPort).getAuthenticateUserId();
            verify(assignmentEmployeePort).getAssignment(restaurantId, authenticatedUserId);
            verify(orderPersistencePort).getOrdersByStatus(restaurantId, pageSize, page, status);
        }


        @Test
        void getOrdersByStatus_Fail_restaurantNotFound() {

            Long restaurantId = DataConstants.DEFAULT_RESTAURANT_ID;
            Integer pageSize = DataConstants.DEFAULT_PAGE_SIZE;
            Integer page = DataConstants.DEFAULT_PAGE;
            String status = OrderStatus.PENDING.getMessage();

            when(restaurantPersistencePort.getById(restaurantId)).thenReturn(Optional.empty()); // Restaurante no existe

            assertThrows(RestaurantNotFoundException.class, () -> {
                orderUseCases.getOrdersByStatus(restaurantId, pageSize, page, status);
            });
            verify(restaurantPersistencePort).getById(restaurantId);
            verify(orderPersistencePort, never()).getOrdersByStatus(anyLong(), anyInt(), anyInt(), anyString());
        }

        @Test
        void getOrdersByStatus_Fail_employeeNotAssigned() {

            Long restaurantId = DataConstants.DEFAULT_RESTAURANT_ID;
            Integer pageSize = DataConstants.DEFAULT_PAGE_SIZE;
            Integer page = DataConstants.DEFAULT_PAGE;
            String status = OrderStatus.PENDING.getMessage();
            Long authenticatedUserId = DataConstants.DEFAULT_USER_ID;
            Restaurant mockRestaurant = DataDomainFactory.createRestaurant();

            when(authenticationPort.getAuthenticateUserId()).thenReturn(authenticatedUserId);
            when(restaurantPersistencePort.getById(restaurantId)).thenReturn(Optional.of(mockRestaurant));
            when(assignmentEmployeePort.getAssignment(restaurantId, authenticatedUserId)).thenReturn(Optional.empty());

            ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class, () -> {
                orderUseCases.getOrdersByStatus(restaurantId, pageSize, page, status);
            });
            assertEquals(Constants.EMPLOYEE_NOT_ALLOWED, exception.getMessage());
            verify(assignmentEmployeePort).getAssignment(restaurantId, authenticatedUserId);
            verify(orderPersistencePort, never()).getOrdersByStatus(anyLong(), anyInt(), anyInt(), anyString());
        }
    }


    @Nested
    @DisplayName("updateStatusOrder")
    class UpdateStatusOrderTests {
        private final Order orderFound = DataDomainFactory.createOrderBd();
        private final Long orderId = DataConstants.DEFAULT_ORDER_ID;
        private final Long restaurantId = DataConstants.DEFAULT_RESTAURANT_ID;
        private final Long authenticatedChefId = DataConstants.DEFAULT_USER_ID;
        private final Long clientId = DataConstants.DEFAULT_ORDER_CLIENT_ID;

        private MockedStatic<Utilities> utilitiesMockedStatic;


        private void mockCommonUpdateValidations() {
            when(authenticationPort.getAuthenticateUserId()).thenReturn(authenticatedChefId);
            when(assignmentEmployeePort.getAssignment(restaurantId, authenticatedChefId))
                    .thenReturn(Optional.of(DataDomainFactory.createAssignmentEmployee()));
        }

        @BeforeEach
        void setup(){
            utilitiesMockedStatic = Mockito.mockStatic(Utilities.class);
        }

        @AfterEach
        void tearDown() {
            if (utilitiesMockedStatic != null) {
                utilitiesMockedStatic.close();
            }
        }

        @Test
        void updateStatusOrder_InPreparation_shouldSucceed() {
            orderFound.setStatus(OrderStatus.PENDING);
            String newStatusString = OrderStatus.IN_PREPARATION.getMessage();

            when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(orderFound));
            mockCommonUpdateValidations();

            orderUseCases.updateStatusOrder(orderId, newStatusString, null);

            verify(orderPersistencePort).findById(orderId);
            verify(authenticationPort).getAuthenticateUserId();
            verify(assignmentEmployeePort).getAssignment(restaurantId, authenticatedChefId);
            ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
            verify(orderPersistencePort).upsertOrder(orderCaptor.capture());

            Order updatedOrder = orderCaptor.getValue();
            assertEquals(OrderStatus.IN_PREPARATION, updatedOrder.getStatus());
            assertEquals(authenticatedChefId, updatedOrder.getChefId());
        }

        @Test
        void updateStatusOrder_ToPrepared_shouldSucceedAndNotify() {
            orderFound.setStatus(OrderStatus.IN_PREPARATION);
            String newStatusString = OrderStatus.PREPARED.getMessage();
            User clientUser = DataDomainFactory.createUserClient();
            String generatedPin = DataConstants.DEFAULT_ORDER_CLIENT_PIN_CODE;

            when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(orderFound));
            mockCommonUpdateValidations();
            utilitiesMockedStatic.when(Utilities::generateRandomPin).thenReturn(generatedPin);
            when(userVerificationPort.getUserInfo(clientId)).thenReturn(Optional.of(clientUser));

            orderUseCases.updateStatusOrder(orderId, newStatusString, null);

            verify(orderPersistencePort).findById(orderId);
            ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
            verify(orderPersistencePort).upsertOrder(orderCaptor.capture());
            Order updatedOrder = orderCaptor.getValue();

            assertEquals(OrderStatus.PREPARED, updatedOrder.getStatus());
            assertEquals(generatedPin, updatedOrder.getCodeValidation());
            verify(notificationPort).sendTextMessage(anyString(), eq(clientUser.getPhoneNumber()));
        }

        @Test
        void updateStatusOrder_ToDelivered_shouldSucceed() {
            orderFound.setStatus(OrderStatus.PREPARED);
            orderFound.setCodeValidation(DataConstants.DEFAULT_ORDER_CLIENT_PIN_CODE);
            String newStatusString = OrderStatus.DELIVERED.getMessage();

            when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(orderFound));
            mockCommonUpdateValidations();

            orderUseCases.updateStatusOrder(orderId, newStatusString, DataConstants.DEFAULT_ORDER_CLIENT_PIN_CODE);

            verify(orderPersistencePort).findById(orderId);
            ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
            verify(orderPersistencePort).upsertOrder(orderCaptor.capture());
            Order updatedOrder = orderCaptor.getValue();

            assertEquals(OrderStatus.DELIVERED, updatedOrder.getStatus());
        }

        @Test
        void updateStatusOrder_ToDelivered_incorrectPin_shouldThrowNotAllowedValueException() {
            orderFound.setStatus(OrderStatus.PREPARED);
            orderFound.setCodeValidation(DataConstants.DEFAULT_ORDER_CLIENT_PIN_CODE);
            String newStatusString = OrderStatus.DELIVERED.getMessage();
            String incorrectPinAttempt = "111111";

            when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(orderFound));
            mockCommonUpdateValidations();

            NotAllowedValueException exception = assertThrows(NotAllowedValueException.class, () -> {
                orderUseCases.updateStatusOrder(orderId, newStatusString, incorrectPinAttempt);
            });
            assertEquals(Constants.CLIENT_PIN_CODE_INCORRECT, exception.getMessage());
            verify(orderPersistencePort, never()).upsertOrder(any(Order.class));
        }

        @Test
        void updateStatusOrder_invalidInitialStatus_shouldThrowActionNotAllowedException() {
            orderFound.setStatus(OrderStatus.DELIVERED);
            String newStatusStringAttempt = OrderStatus.PREPARED.getMessage();

            when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(orderFound));
            mockCommonUpdateValidations();

            ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class, () -> {
                orderUseCases.updateStatusOrder(orderId, newStatusStringAttempt, null);
            });
            assertEquals(Constants.ORDER_STATUS_ACTION_NOT_ALLOWED, exception.getMessage());
            verify(orderPersistencePort, never()).upsertOrder(any(Order.class));
        }

        @Test
        void updateStatusOrder_orderNotFound_shouldThrowOrderNotFoundException() {
            when(orderPersistencePort.findById(orderId)).thenReturn(Optional.empty());
            String newStatusString = OrderStatus.IN_PREPARATION.getMessage();

            assertThrows(OrderNotFoundException.class, () -> {
                orderUseCases.updateStatusOrder(orderId, newStatusString, null);
            });
            verify(orderPersistencePort, never()).upsertOrder(any(Order.class));
        }

        @Test
        void updateStatusOrder_employeeNotAssigned_shouldThrowActionNotAllowedException() {
            orderFound.setStatus(OrderStatus.PENDING);
            String newStatusString = OrderStatus.IN_PREPARATION.getMessage();

            when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(orderFound));
            when(authenticationPort.getAuthenticateUserId()).thenReturn(authenticatedChefId);
            when(assignmentEmployeePort.getAssignment(restaurantId, authenticatedChefId))
                    .thenReturn(Optional.empty());

            ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class, () -> {
                orderUseCases.updateStatusOrder(orderId, newStatusString, null);
            });
            assertEquals(Constants.EMPLOYEE_NOT_ALLOWED, exception.getMessage());
            verify(orderPersistencePort, never()).upsertOrder(any(Order.class));
        }
    }

}