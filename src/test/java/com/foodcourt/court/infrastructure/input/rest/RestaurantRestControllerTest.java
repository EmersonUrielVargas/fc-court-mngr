package com.foodcourt.court.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodcourt.court.application.DataApplicationFactory;
import com.foodcourt.court.application.dto.request.AssignEmployeeRequestDto;
import com.foodcourt.court.application.dto.request.RestaurantRequestDto;
import com.foodcourt.court.application.dto.response.GetOrderResponseDto;
import com.foodcourt.court.application.dto.response.PlatesByRestaurantResponseDto;
import com.foodcourt.court.application.dto.response.RestaurantItemResponseDto;
import com.foodcourt.court.application.handler.IOrderHandler;
import com.foodcourt.court.application.handler.IPlateHandler;
import com.foodcourt.court.application.handler.IRestaurantHandler;
import com.foodcourt.court.domain.DataDomainFactory;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.OrderStatus;
import com.foodcourt.court.domain.exception.DomainException;
import com.foodcourt.court.domain.exception.NotAllowedValueException;
import com.foodcourt.court.domain.exception.RestaurantNotFoundException;
import com.foodcourt.court.domain.utilities.CustomPage;
import com.foodcourt.court.infrastructure.exceptionhandler.ControllerAdvisor;
import com.foodcourt.court.shared.DataConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RestaurantRestControllerTest {

    @InjectMocks
    private RestaurantRestController restaurantRestController;

    private ObjectMapper objectMapper;

    @Mock
    private IRestaurantHandler restaurantHandler;

    @Mock
    private IPlateHandler plateHandler;

    @Mock
    private IOrderHandler orderHandler;
    private MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantRestController)
                .setControllerAdvice(new ControllerAdvisor())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayName("POST v1/restaurant")
    class createRestaurantsTests {
        private final String pathTest ="/v1/restaurant";

        @Test
        void createRestaurantSuccessful() throws Exception {
            String jsonBody = objectMapper.writeValueAsString(DataApplicationFactory.createRestaurantRequestDto());

            doNothing().when(restaurantHandler).create(any(RestaurantRequestDto.class));
            MockHttpServletRequestBuilder request = post(pathTest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        @Test
        void createRestaurantFail() throws Exception {
            RestaurantRequestDto restaurantBody = DataApplicationFactory.createRestaurantRequestDto();
            restaurantBody.setName("40066");
            String jsonBody = objectMapper.writeValueAsString(restaurantBody);
            doThrow(new DomainException("fail domain validation data")).when(restaurantHandler).create(any(RestaurantRequestDto.class));
            MockHttpServletRequestBuilder request = post(pathTest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("GET v1/restaurant")
    class getRestaurantsTests {
        private final String pathTest ="/v1/restaurant";
        @Test
        void getRestaurantSuccessful() throws Exception {
            RestaurantItemResponseDto restaurantsResponseDto = DataApplicationFactory.createListRestaurantDtoRs();
            List<RestaurantItemResponseDto> listRestaurants = List.of(restaurantsResponseDto);
            CustomPage<RestaurantItemResponseDto> pageRestaurants = DataDomainFactory.createEmptyCustomPage();
            pageRestaurants.setData(listRestaurants);
            String jsonBodyResponse = objectMapper.writeValueAsString(pageRestaurants);

            when(restaurantHandler.getRestaurants(anyInt(), anyInt())).thenReturn(pageRestaurants);
            MockHttpServletRequestBuilder request = get(pathTest)
                    .param(Constants.PAGE_SIZE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE_SIZE))
                    .param(Constants.PAGE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(jsonBodyResponse));

        }

        @Test
        void getRestaurantFailInvalidParams() throws Exception {
            doThrow(new NotAllowedValueException("invalid Params")).when(restaurantHandler).getRestaurants(anyInt(), anyInt());
            MockHttpServletRequestBuilder request = get(pathTest)
                    .param(Constants.PAGE_SIZE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE_SIZE))
                    .param(Constants.PAGE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE))
                    .contentType(MediaType.APPLICATION_JSON);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("GET v1/restaurant/{restaurantId}/plates")
    class getPlatesByRestaurantsTests {
        private final String pathTest ="/v1/restaurant/1/plates";
        @Test
        void getPlatesByRestaurant_Successful_withoutCategory() throws Exception {
            List<PlatesByRestaurantResponseDto> plates = DataApplicationFactory.createListPlatesByRestaurantResponseDto();
            CustomPage<PlatesByRestaurantResponseDto> pagePlates = DataDomainFactory.createCustomPage(plates);
            String jsonBodyResponse = objectMapper.writeValueAsString(pagePlates);

            when(plateHandler.getPlatesByRestaurant(anyLong(),anyInt(), anyInt(), eq(null))).thenReturn(pagePlates);
            MockHttpServletRequestBuilder request = get(pathTest)
                    .param(Constants.PAGE_SIZE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE_SIZE))
                    .param(Constants.PAGE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(jsonBodyResponse));

        }

        @Test
        void getPlatesByRestaurant_Successful_withCategory() throws Exception {
            List<PlatesByRestaurantResponseDto> plates = DataApplicationFactory.createListPlatesByRestaurantResponseDto();
            CustomPage<PlatesByRestaurantResponseDto> pagePlates = DataDomainFactory.createCustomPage(plates);
            String jsonBodyResponse = objectMapper.writeValueAsString(pagePlates);

            when(plateHandler.getPlatesByRestaurant(anyLong(),anyInt(), anyInt(), anyLong())).thenReturn(pagePlates);
            MockHttpServletRequestBuilder request = get(pathTest)
                    .param(Constants.PAGE_SIZE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE_SIZE))
                    .param(Constants.PAGE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE))
                    .param(Constants.CATEGORY_ID_PARAM_NAME, String.valueOf(DataConstants.DEFAULT_CATEGORY_ID))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(jsonBodyResponse));

        }

        @Test
        void getPlatesByRestaurant_Fail_InvalidParams() throws Exception {
            doThrow(new NotAllowedValueException("invalid Params")).when(plateHandler).getPlatesByRestaurant(anyLong(),anyInt(), anyInt(), eq(null));
            MockHttpServletRequestBuilder request = get(pathTest)
                    .param(Constants.PAGE_SIZE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE_SIZE))
                    .param(Constants.PAGE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE))
                    .contentType(MediaType.APPLICATION_JSON);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("GET v1/restaurant/{restaurantId}/orders")
    class getOrdersByRestaurantsTests {
        private final String pathTest ="/v1/restaurant/1/orders";
        @Test
        void getOrdersByRestaurant_Successful() throws Exception {
            List<GetOrderResponseDto> orders = DataApplicationFactory.createListGetOrderResponseDto();
            CustomPage<GetOrderResponseDto> pageOrders = DataDomainFactory.createCustomPage(orders);
            String jsonBodyResponse = objectMapper.writeValueAsString(pageOrders);

            when(orderHandler.getOrdersByStatus(anyLong(),anyInt(), anyInt(), anyString())).thenReturn(pageOrders);
            MockHttpServletRequestBuilder request = get(pathTest)
                    .param(Constants.PAGE_SIZE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE_SIZE))
                    .param(Constants.PAGE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE))
                    .param(Constants.ORDER_STATUS_PARAM_NAME, String.valueOf(OrderStatus.PENDING.getMessage()))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(jsonBodyResponse));

        }

        @Test
        void getOrdersByRestaurant_Fail_InvalidParams() throws Exception {
            MockHttpServletRequestBuilder request = get(pathTest)
                    .param(Constants.PAGE_SIZE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE_SIZE))
                    .param(Constants.PAGE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE))
                    .contentType(MediaType.APPLICATION_JSON);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("POST v1/restaurant/assignment")
    class createEmployeeAssignmentTests {
        private final String pathTest ="/v1/restaurant/assignment";

        @Test
        void createEmployeeAssignmentSuccessful() throws Exception {
            String jsonBody = objectMapper.writeValueAsString(DataApplicationFactory.createAssignEmployeeRequestDto());

            doNothing().when(restaurantHandler).assignEmployee(any(AssignEmployeeRequestDto.class));
            MockHttpServletRequestBuilder request = post(pathTest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        @Test
        void createEmployeeAssignmentFail() throws Exception {
            String jsonBody = objectMapper.writeValueAsString(DataApplicationFactory.createAssignEmployeeRequestDto());
            doThrow(new RestaurantNotFoundException(Constants.RESTAURANT_OWNER_NO_FOUND)).when(restaurantHandler).assignEmployee(any(AssignEmployeeRequestDto.class));
            MockHttpServletRequestBuilder request = post(pathTest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isConflict());
        }
    }
}