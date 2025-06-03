package com.foodcourt.court.domain.exception;

import com.foodcourt.court.domain.constants.Constants;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException() {
        super(Constants.USER_NO_FOUND);
    }
}
