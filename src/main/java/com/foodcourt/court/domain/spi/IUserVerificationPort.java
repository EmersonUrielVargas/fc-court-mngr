package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.domain.model.User;

import java.util.Optional;

public interface IUserVerificationPort {
    Optional<UserRole> getRolUser(Long userId);
    Optional<User> getUserInfo(Long userId);
}
