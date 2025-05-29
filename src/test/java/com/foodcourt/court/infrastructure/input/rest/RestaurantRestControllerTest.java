package com.foodcourt.court.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodcourt.court.application.DataApplicationFactory;
import com.foodcourt.court.application.dto.request.RestaurantRequestDto;
import com.foodcourt.court.application.dto.response.ListRestaurantsResponseDto;
import com.foodcourt.court.application.handler.IRestaurantHandler;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.exception.DomainException;
import com.foodcourt.court.domain.exception.NotAllowedValueException;
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
            ListRestaurantsResponseDto restaurantsResponseDto = DataApplicationFactory.createListRestaurantDtoRs();
            List<ListRestaurantsResponseDto> listRestaurants = List.of(restaurantsResponseDto);
            String jsonBodyResponse = objectMapper.writeValueAsString(listRestaurants);

            when(restaurantHandler.getRestaurants(anyInt(), anyInt())).thenReturn(listRestaurants);
            MockHttpServletRequestBuilder request = get(pathTest)
                    .param(Constants.PAGE_SIZE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE_SIZE))
                    .param(Constants.PAGE_NAME, String.valueOf(DataConstants.DEFAULT_PAGE))
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
}