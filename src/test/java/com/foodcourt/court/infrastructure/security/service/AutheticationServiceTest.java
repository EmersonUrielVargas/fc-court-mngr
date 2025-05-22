package com.foodcourt.court.infrastructure.security.service;

import com.foodcourt.court.infrastructure.security.dto.UserDetailsDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutheticationServiceTest {

    @InjectMocks
    private AutheticationService autheticationService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Given exists authentication when request userId should return userId")
    void getUserIdSuccessFul() {
        Long expectedUserId = 123L;
        UserDetailsDto userDetailsDtoMock = UserDetailsDto
                .builder()
                .idUser(expectedUserId)
                .build();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetailsDtoMock);

        Long actualUserId = autheticationService.getUserId();

        assertNotNull(actualUserId);
        assertEquals(expectedUserId, actualUserId);
    }

    @Test
    @DisplayName("Given not exists authentication when request userId should throws NullPointerException")
    void getUserIdFailAuthenticationIsNull() {
        when(securityContext.getAuthentication()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            autheticationService.getUserId();
        });
    }
}