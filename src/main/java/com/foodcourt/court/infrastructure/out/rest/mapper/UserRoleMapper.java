package com.foodcourt.court.infrastructure.out.rest.mapper;

import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.domain.exception.DomainException;

public class UserRoleMapper {

    private UserRoleMapper() {
        throw new IllegalStateException("Utility Mapper class");
    }

    public static UserRole toDomain(String roleName) {
        return switch (roleName.trim().toUpperCase()) {
            case "ADMIN" -> UserRole.ADMIN;
            case "OWNER" -> UserRole.OWNER;
            case "EMPLOYEE" -> UserRole.EMPLOYEE;
            case "CLIENT" -> UserRole.CLIENT;
            default -> throw new DomainException(Constants.USER_ROLE_NO_FOUND);
        };
    }
}
