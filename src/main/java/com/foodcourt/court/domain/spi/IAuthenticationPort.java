package com.foodcourt.court.domain.spi;

public interface IAuthenticationPort {
    Long getAuthenticateUserId();
    String getAuthenticateUserEmail();
}
