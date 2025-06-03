package com.foodcourt.court.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodcourt.court.application.DataApplicationFactory;
import com.foodcourt.court.application.dto.request.CreateOrderRequestDto;
import com.foodcourt.court.application.dto.request.UpdateStatusOrderRequestDto;
import com.foodcourt.court.application.handler.IOrderHandler;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.exception.ActionNotAllowedException;
import com.foodcourt.court.domain.exception.DomainException;
import com.foodcourt.court.infrastructure.exceptionhandler.ControllerAdvisor;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderRestControllerTest {

    @InjectMocks
    private OrderRestController orderRestController;

    private ObjectMapper objectMapper;

    @Mock
    private IOrderHandler orderHandler;
    private MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(orderRestController)
                .setControllerAdvice(new ControllerAdvisor())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayName("POST /v1/order")
    class createOrderTests {
        private final String pathTest ="/v1/order";

        @Test
        void createOrderSuccessful() throws Exception {
            String jsonBody = objectMapper.writeValueAsString(DataApplicationFactory.createOrderRequestDto());

            doNothing().when(orderHandler).create(any(CreateOrderRequestDto.class));
            MockHttpServletRequestBuilder request = post(pathTest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        @Test
        void createOrderFail() throws Exception {
            String jsonBody = objectMapper.writeValueAsString(DataApplicationFactory.createOrderRequestDto());
            doThrow(new DomainException(Constants.CLIENT_HAS_ORDERS_ACTIVE)).when(orderHandler).create(any(CreateOrderRequestDto.class));
            MockHttpServletRequestBuilder request = post(pathTest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("PATCH /v1/order/status")
    class updateStatusOrderTests {
        private final String pathTest ="/v1/order/status";

        @Test
        void updateStatusOrderSuccessful() throws Exception {
            String jsonBody = objectMapper.writeValueAsString(DataApplicationFactory.createUpdateStatusOrderRequestDto());

            doNothing().when(orderHandler).updateStatusOrder(any(UpdateStatusOrderRequestDto.class));
            MockHttpServletRequestBuilder request = patch(pathTest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void updateStatusOrderFail() throws Exception {
            String jsonBody = objectMapper.writeValueAsString(DataApplicationFactory.createUpdateStatusOrderRequestDto());

            doThrow(new ActionNotAllowedException(Constants.ORDER_STATUS_ACTION_NOT_ALLOWED)).when(orderHandler).updateStatusOrder(any(UpdateStatusOrderRequestDto.class));
            MockHttpServletRequestBuilder request = patch(pathTest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isConflict());
        }
    }
}