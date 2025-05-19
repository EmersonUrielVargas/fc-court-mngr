package com.foodcourt.court.infrastructure.out.rest.mapper;

import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleMapperTest {

        @DisplayName("Debe mapear correctamente strings de roles válidos al enum UserRole correspondiente")
        @ParameterizedTest(name = "String ''{0}'' se mapea a UserRole.{1}")
        @CsvSource({
                "ADMIN, ADMIN",
                "OWNER, OWNER",
                "EMPLOYEE, EMPLOYEE",
                "CLIENT, CLIENT",
                "admin, ADMIN",
                " Owner , OWNER",
                "  eMPLoYEE  , EMPLOYEE",
                "clIEnt, CLIENT"
        })
        void toDomainSuccessFul(String roleNameInput, UserRole expectedUserRole) {

            UserRole actualUserRole = UserRoleMapper.toDomain(roleNameInput);
            assertEquals(expectedUserRole, actualUserRole);
        }

        @DisplayName("Debe lanzar DomainException para strings de roles desconocidos")
        @ParameterizedTest(name = "String ''{0}'' debe lanzar DomainException")
        @ValueSource(strings = {"UNKNOWN_ROLE", "EDITOR", "GUEST", "", "  "})
        void toDomain_whenUnknownRoleString_shouldThrowDomainException(String invalidRoleName) {
            DomainException exception = assertThrows(DomainException.class, () -> {
                UserRoleMapper.toDomain(invalidRoleName);
            });
            assertEquals(Constants.USER_ROLE_NO_FOUND, exception.getMessage());
        }
}