package com.foodcourt.court.domain.validators;

import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.domain.exception.DomainException;

import java.time.LocalDate;
import java.time.Period;

public class RestaurantValidator {


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
}
