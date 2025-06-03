package com.foodcourt.court.domain.exception;

import com.foodcourt.court.domain.constants.Constants;

public class OrderNotFoundException extends DomainException {
    public OrderNotFoundException(String message) {
        super(message);
    }
    public OrderNotFoundException() {
        super(Constants.ORDER_NO_FOUND);
    }
}
