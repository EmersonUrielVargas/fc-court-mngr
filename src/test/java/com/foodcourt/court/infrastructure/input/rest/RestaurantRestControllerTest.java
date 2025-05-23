package com.foodcourt.court.infrastructure.input.rest;

import com.foodcourt.court.application.dto.request.RestaurantRequestDto;
import com.foodcourt.court.application.handler.IRestaurantHandler;
import com.foodcourt.court.domain.exception.DomainException;
import com.foodcourt.court.infrastructure.exceptionhandler.ControllerAdvisor;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RestaurantRestControllerTest {

    @InjectMocks
    private RestaurantRestController restaurantRestController;

    @Mock
    private IRestaurantHandler restaurantHandler;
    private MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantRestController)
                .setControllerAdvice(new ControllerAdvisor())
                .build();
    }

    @Test
    void createRestaurantSuccessful() throws Exception {
        String jsonBody = """
            {
               "name": "El Gran Sabor",
               "address": "Calle Falsa 123, Ciudad Ejemplo",
               "ownerId": 10,
               "phoneNumber": "+573001234567",
               "urlLogo": "https://ejemplo.com/logos/elgransabor.png",
               "nit": "9001234567"
             }
        """;

        doNothing().when(restaurantHandler).create(any(RestaurantRequestDto.class));
        MockHttpServletRequestBuilder request = post("/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody);
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void createRestaurantFail() throws Exception {
        String jsonBody = """
            {
               "name": "400",
               "address": "Calle Falsa 123, Ciudad Ejemplo",
               "ownerId": 10,
               "phoneNumber": "+573001234567",
               "urlLogo": "https://ejemplo.com/logos/elgransabor.png",
               "nit": "9001234567"
             }
        """;
        doThrow(new DomainException("fail domain validation data")).when(restaurantHandler).create(any(RestaurantRequestDto.class));
        MockHttpServletRequestBuilder request = post("/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody);
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isConflict());
    }
}