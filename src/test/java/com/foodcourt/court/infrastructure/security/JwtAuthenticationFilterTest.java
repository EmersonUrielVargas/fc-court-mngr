package com.foodcourt.court.infrastructure.security;

import com.foodcourt.court.infrastructure.security.dto.UserDetailsDto;
import com.foodcourt.court.infrastructure.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import java.io.IOException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("GIVEN Chain arrive WHEN no found authorization header THEN should doFilterInternal continue to next chain")
    void doFilterInternalNoAuthorizationHeader() throws ServletException, IOException {
        when(request.getHeader(JwtAuthenticationFilter.AUTH_HEADER_NAME)).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, never()).extractUserEmail(anyString());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("GIVEN Chain arrive WHEN authorization header not start with bearer THEN should doFilterInternal continue to next chain")
    void doFilterInternalAuthHeaderNotStartingWithBearer() throws ServletException, IOException {
        when(request.getHeader(JwtAuthenticationFilter.AUTH_HEADER_NAME)).thenReturn("InvalidPrefix token");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, never()).extractUserEmail(anyString());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("GIVEN Chain arrive WHEN email extracted is empty THEN should doFilterInternal continue to next chain")
    void doFilterInternalNullUserEmail() throws ServletException, IOException {
        String token = "someValidToken";
        when(request.getHeader(JwtAuthenticationFilter.AUTH_HEADER_NAME)).thenReturn(JwtAuthenticationFilter.AUTH_TOKEN_PREFIX + token);
        when(jwtService.extractUserEmail(token)).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, times(1)).extractUserEmail(token);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("GIVEN Chain arrive WHEN authentication already exist THEN should doFilterInternal continue to next chain")
    void doFilterInternalAuthenticationAlreadyExists() throws ServletException, IOException {
        String token = "someValidToken";
        String userEmail = "test@example.com";
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(request.getHeader(JwtAuthenticationFilter.AUTH_HEADER_NAME)).thenReturn(JwtAuthenticationFilter.AUTH_TOKEN_PREFIX + token);
        when(jwtService.extractUserEmail(token)).thenReturn(userEmail);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, times(1)).extractUserEmail(token);
        verify(jwtService, never()).extractClaim(eq(token), any());
        verify(jwtService, never()).isTokenValid(token);
    }

    @Test
    @DisplayName("GIVEN Chain arrive WHEN token is invalid THEN should doFilterInternal continue to next chain")
    void doFilterInternalInvalidToken() throws ServletException, IOException {
        String token = "invalidOrExpiredToken";
        String userEmail = "test@example.com";
        Long userId = 1L;
        String roleString = "OWNER";

        when(request.getHeader(JwtAuthenticationFilter.AUTH_HEADER_NAME)).thenReturn(JwtAuthenticationFilter.AUTH_TOKEN_PREFIX + token);
        when(jwtService.extractUserEmail(token)).thenReturn(userEmail);
        when(jwtService.isTokenValid(token)).thenReturn(false);

        when(jwtService.extractClaim(eq(token), any(Function.class)))
                .thenReturn(roleString)
                .thenReturn(userId);


        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, times(1)).extractUserEmail(token);
        verify(jwtService, times(1)).isTokenValid(token);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("GIVEN Chain arrive WHEN token is valid THEN should doFilterInternal authenticate")
    void doFilterInternalAllowAuthentication() throws ServletException, IOException {
        String token = "validToken123";
        String userEmail = "user@example.com";
        Long userId = 1L;
        String roleString = "OWNER";

        when(request.getHeader(JwtAuthenticationFilter.AUTH_HEADER_NAME)).thenReturn(JwtAuthenticationFilter.AUTH_TOKEN_PREFIX + token);
        when(jwtService.extractUserEmail(token)).thenReturn(userEmail);
        when(jwtService.isTokenValid(token)).thenReturn(true);

        when(jwtService.extractClaim(eq(token), any(Function.class)))
                .thenReturn(roleString)
                .thenReturn(userId);


        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, times(1)).extractUserEmail(token);
        verify(jwtService, times(1)).isTokenValid(token);
        verify(jwtService, times(2)).extractClaim(eq(token), any(Function.class));

        Authentication authenticationObj = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authenticationObj);
        assertTrue(authenticationObj.isAuthenticated());
        assertInstanceOf(UserDetailsDto.class, authenticationObj.getPrincipal());

        UserDetailsDto userDetails = (UserDetailsDto) authenticationObj.getPrincipal();
        assertEquals(userEmail, userDetails.getEmail());
    }

}