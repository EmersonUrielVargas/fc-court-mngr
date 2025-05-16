package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.enums.UserRole;

import java.util.Optional;

public interface IUserVerificationPort {
    Optional<UserRole> gerRolUser(Long userId);
}
