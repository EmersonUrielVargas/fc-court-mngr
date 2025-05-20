package com.foodcourt.court.domain.validators;

import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.domain.exception.DomainException;

import java.util.Optional;

public class UtilitiesValidator {

    private UtilitiesValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validateOwner(UserRole rolFound){
        if (rolFound != UserRole.OWNER){
             throw new DomainException(Constants.USER_NO_AUTHORIZED);
        }
    }

    public static void validateStringPattern(String value, String pattern, String errorMessage){
        if (!value.matches(pattern)){
            throw new DomainException(errorMessage);
        }
    }

    public static void validatePrice(Integer value){
        if (value < Constants.MIN_PRICE_ALLOW){
            throw new DomainException(Constants.PRICE_NOT_ALLOWED);
        }
    }

    public static <T> T getDefaultIsNullOrEmpty(T primaryValue, T defaultValue){
        return Optional.ofNullable(primaryValue)
                .filter(value-> !(value instanceof String stringValue) || !stringValue.isBlank())
                .orElse(defaultValue);
    }

}
