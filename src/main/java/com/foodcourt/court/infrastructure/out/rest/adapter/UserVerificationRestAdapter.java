package com.foodcourt.court.infrastructure.out.rest.adapter;

import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.domain.model.User;
import com.foodcourt.court.domain.spi.IUserVerificationPort;
import com.foodcourt.court.infrastructure.out.rest.client.IUserRestClient;
import com.foodcourt.court.infrastructure.out.rest.mapper.UserRoleMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserVerificationRestAdapter implements IUserVerificationPort {

    private final IUserRestClient userRestClient;

    @Override
    public Optional<UserRole> getRolUser(Long userId) {
        try {
            UserRole userRole = UserRoleMapper.toDomain(userRestClient.getUserRole(userId).getUserRole());
            return Optional.of(userRole);
        }catch (FeignException.NotFound exception){
            return  Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserInfo(Long userId) {
        try {
            User user =userRestClient.getUserInfo(userId);
            return Optional.of(user);
        }catch (FeignException.NotFound exception){
            return  Optional.empty();
        }
    }
}
