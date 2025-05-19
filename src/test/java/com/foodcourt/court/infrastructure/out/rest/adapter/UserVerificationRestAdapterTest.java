package com.foodcourt.court.infrastructure.out.rest.adapter;

import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.infrastructure.out.rest.client.IUserRestClient;
import com.foodcourt.court.infrastructure.out.rest.dto.response.UserRoleResponseDto;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserVerificationRestAdapterTest {

    @Mock
    private IUserRestClient userRestClient;

    @InjectMocks
    private UserVerificationRestAdapter userVerificationRestAdapter;


    @Test
    void getRolUserShouldReturnOptionalOfUserRole() {
        Long userId = 1L;
        String roleNameFromFeign = "OWNER";
        UserRole userRoleName = UserRole.OWNER;

        UserRoleResponseDto response =  new UserRoleResponseDto();
        response.setUserRole(roleNameFromFeign);

        when(userRestClient.getUserRole(userId)).thenReturn(response);
        Optional<UserRole> actualUserRoleOptional = userVerificationRestAdapter.getRolUser(userId);

        assertTrue(actualUserRoleOptional.isPresent());
        assertEquals(userRoleName, actualUserRoleOptional.get());

        verify(userRestClient).getUserRole(userId);
    }

    @Test
    void getRolUserThrowsFeignExceptionNotFound() {
        Long userId = 2L;
        Request dummyRequest = Request.create(Request.HttpMethod.GET, "/api/v1/mngr/users/role/10", Collections.emptyMap(), null, new RequestTemplate());
        FeignException.NotFound notFoundException = new FeignException.NotFound(
                "User not found",
                dummyRequest,
                null,
                Collections.emptyMap()
        );
        when(userRestClient.getUserRole(userId)).thenThrow(notFoundException);

        Optional<UserRole> actualUserRoleOptional = userVerificationRestAdapter.getRolUser(userId);

        assertTrue(actualUserRoleOptional.isEmpty());
        verify(userRestClient, times(1)).getUserRole(userId);
    }
}