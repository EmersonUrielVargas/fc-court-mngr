package com.foodcourt.court.domain.validators;

import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.domain.exception.DomainException;
import com.foodcourt.court.domain.exception.MissingParamRequiredException;
import com.foodcourt.court.domain.exception.NotAllowedValueException;

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
            throw new NotAllowedValueException(errorMessage);
        }
    }

    public static void validatePrice(Integer value){
        if (value < Constants.MIN_PRICE_ALLOW){
            throw new NotAllowedValueException(Constants.PRICE_NOT_ALLOWED);
        }
    }

    public static void validateNotNegativeNumber(Integer value, String paramName){
        if (value < Constants.MIN_PAGE_ALLOW){
            throw new NotAllowedValueException(String.format(Constants.NEGATIVE_VALUE_PARAM_TEMPLATE, paramName));
        }
    }

    public static <T> T getDefaultIsNullOrEmpty(T primaryValue, T defaultValue){
        return Optional.ofNullable(primaryValue)
                .filter(value-> !(value instanceof String stringValue) || !stringValue.isBlank())
                .orElse(defaultValue);
    }

    public static <T> void validateIsNull(T value){
        Optional.ofNullable(value)
                .filter(valueFiltered -> !(valueFiltered instanceof String stringValue) || !stringValue.isBlank())
                .orElseThrow(MissingParamRequiredException::new);
    }

}
