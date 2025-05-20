package com.foodcourt.court.infrastructure.input.rest;

import com.foodcourt.court.application.dto.request.CreatePlateRequestDto;
import com.foodcourt.court.application.handler.IPlateHandler;
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
class PlateRestControllerTest {

    @InjectMocks
    private PlateRestController plateRestController;

    @Mock
    private IPlateHandler plateHandler;
    private MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(plateRestController)
                .setControllerAdvice(new ControllerAdvisor())
                .build();
    }

    @Test
    void createPlateSuccessful() throws Exception {
        String jsonBody = """
            {
               "name": "Pizza Margarita Especial",
                "price": 25000,
                "categoryId": 10,
                "description": "Deliciosa pizza con base de tomate fresco.",
                "urlImage": "https://ejemplo.com/imagenes/pizza_margarita.jpg",
                "restaurantId": 1
             }
        """;

        doNothing().when(plateHandler).create(any(CreatePlateRequestDto.class));
        MockHttpServletRequestBuilder request = post("/api/v1/mngr/court/plate")
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
                "name": "Pizza Margarita Especial",
                "price": 25000,
                "categoryId": 10,
                "description": "Deliciosa pizza con base de tomate fresco.",
                "urlImage": "https://ejemplo.com/imagenes/pizza_margarita.jpg",
                "restaurantId": 1
             }
        """;
        doThrow(new DomainException("fail domain validation data")).when(plateHandler).create(any(CreatePlateRequestDto.class));
        MockHttpServletRequestBuilder request = post("/api/v1/mngr/court/plate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody);
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isConflict());
    }
}