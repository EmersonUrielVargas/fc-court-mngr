package com.foodcourt.court.domain.exception;

import com.foodcourt.court.domain.constants.Constants;

public class RestaurantNotFoundException extends DomainException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }
    public RestaurantNotFoundException() {
        super(Constants.RESTAURANT_NO_FOUND);
    }
}
