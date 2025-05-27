package com.foodcourt.court.infrastructure.security.adapter;

import com.foodcourt.court.domain.spi.IAuthenticationPort;
import com.foodcourt.court.infrastructure.security.service.AutheticationService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationAdapter implements IAuthenticationPort {

    private final AutheticationService autheticationService;

    @Override
    public Long getAuthenticateUserId() {
        return autheticationService.getUserId();
    }
}
