package com.foodcourt.court.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodcourt.court.application.DataApplicationFactory;
import com.foodcourt.court.application.dto.request.CreatePlateRequestDto;
import com.foodcourt.court.application.dto.request.StatusPlateRequestDto;
import com.foodcourt.court.application.dto.request.UpdatePlateRequestDto;
import com.foodcourt.court.application.handler.IPlateHandler;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PlateRestControllerTest {

    @InjectMocks
    private PlateRestController plateRestController;

    @Mock
    private IPlateHandler plateHandler;
    @Mock
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(plateRestController)
                .setControllerAdvice(new ControllerAdvisor())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayName("POST v1/plate")
    class createPlateTests{
        private final String pathTest = "/v1/plate";

        @Test
        void createPlateSuccessful() throws Exception {
            CreatePlateRequestDto createPlateRequestDto = DataApplicationFactory.createPlateRequestDto();
            String jsonBody = objectMapper.writeValueAsString(createPlateRequestDto);

            doNothing().when(plateHandler).create(any(CreatePlateRequestDto.class));
            MockHttpServletRequestBuilder request = post(pathTest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        @Test
        void createRestaurantFail() throws Exception {
            CreatePlateRequestDto createPlateRequestDto = DataApplicationFactory.createPlateRequestDto();
            String jsonBody = objectMapper.writeValueAsString(createPlateRequestDto);

            doThrow(new DomainException("fail domain validation data")).when(plateHandler).create(any(CreatePlateRequestDto.class));
            MockHttpServletRequestBuilder request = post(pathTest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isConflict());
        }

    }

    @Nested
    @DisplayName("PATCH v1/plate")
    class updatePlateTests{

        private final String pathTest = "/v1/plate";


        @Test
        void updatePlateSuccessful() throws Exception {
            UpdatePlateRequestDto updatePlateRequestDto = DataApplicationFactory.createUpdatePlateRequestDto();
            String jsonBody = objectMapper.writeValueAsString(updatePlateRequestDto);

            doNothing().when(plateHandler).update(any(UpdatePlateRequestDto.class));
            MockHttpServletRequestBuilder request = patch(pathTest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void updatePlateFail() throws Exception {
            UpdatePlateRequestDto updatePlateRequestDto = DataApplicationFactory.createUpdatePlateRequestDto();
            String jsonBody = objectMapper.writeValueAsString(updatePlateRequestDto);
            doThrow(new DomainException("fail domain validation data")).when(plateHandler).update(any(UpdatePlateRequestDto.class));
            MockHttpServletRequestBuilder request = patch(pathTest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isConflict());
        }

    }

    @Nested
    @DisplayName("PATCH v1/plate/status")
    class setStatusPlateTests{

        private final String pathTest = "/v1/plate/status";

        @Test
        void setStatusPlateSuccessful() throws Exception {
            StatusPlateRequestDto statusPlateRequestDto = DataApplicationFactory.createStatusPlateRequestDto();
            String jsonBody = objectMapper.writeValueAsString(statusPlateRequestDto);

            doNothing().when(plateHandler).setStatus(any(StatusPlateRequestDto.class));
            MockHttpServletRequestBuilder request = patch(pathTest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void setStatusPlateFail() throws Exception {
            StatusPlateRequestDto statusPlateRequestDto = DataApplicationFactory.createStatusPlateRequestDto();
            String jsonBody = objectMapper.writeValueAsString(statusPlateRequestDto);
            doThrow(new DomainException("fail domain validation data")).when(plateHandler).setStatus(any(StatusPlateRequestDto.class));
            MockHttpServletRequestBuilder request = patch(pathTest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody);
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isConflict());
        }

    }

}