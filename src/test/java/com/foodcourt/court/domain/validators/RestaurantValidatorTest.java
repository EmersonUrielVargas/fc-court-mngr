package com.foodcourt.court.domain.validators;

import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.domain.exception.DomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantValidatorTest {

    @Test
    void validateOwnerSuccessFul() {
        UserRole role =  UserRole.OWNER;
        assertDoesNotThrow(()->RestaurantValidator.validateOwner(role));
    }

    @Test
    void validateOwnerThrowsException() {
        UserRole role =  UserRole.CLIENT;
        DomainException exception = assertThrows(DomainException.class, ()->RestaurantValidator.validateOwner(role));
        assertEquals(Constants.USER_NO_AUTHORIZED, exception.getMessage());
    }

    @ParameterizedTest(name = "Test String Patterns {index} => stringValue={0}, stringPattern={1}")
    @CsvSource(value = {
            "El gran Tazon:"+Constants.NAME_RESTAURANT_PATTERN+":"+Constants.INVALID_NAME,
            "12312434:"+Constants.ID_NUMBER_PATTERN+":"+Constants.INVALID_ID_NUMBER,
            "+54123456777:"+Constants.PHONE_NUMBER_PATTERN+":"+Constants.INVALID_PHONE_NUMBER
    }, delimiter = ':')
    void validateSuccessfulStringPattern(String value, String pattern, String errorMessage) {
        assertDoesNotThrow(()->RestaurantValidator.validateStringPattern(value, pattern, errorMessage));
    }

    @ParameterizedTest(name = "Test String Patterns throws exception {index} => stringValue={0}, errorMessage={2}")
    @CsvSource(value = {
            "323:"+Constants.NAME_RESTAURANT_PATTERN+":"+Constants.INVALID_NAME,
            "12312434RS:"+Constants.ID_NUMBER_PATTERN+":"+Constants.INVALID_ID_NUMBER,
            "+RT54123456777:"+Constants.PHONE_NUMBER_PATTERN+":"+Constants.INVALID_PHONE_NUMBER
    }, delimiter = ':')
    void validateThrowsExceptionStringPattern(String value, String pattern, String errorMessage) {
        DomainException exception = assertThrows(DomainException.class,
                ()->RestaurantValidator.validateStringPattern(value, pattern, errorMessage)
        );
        assertEquals(errorMessage, exception.getMessage());
    }
}