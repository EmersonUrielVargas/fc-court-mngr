package com.foodcourt.court.domain.exception;

import com.foodcourt.court.domain.constants.Constants;

public class OrderIsEmptyException extends DomainException {
    public OrderIsEmptyException(String message) {
        super(message);
    }
    public OrderIsEmptyException() {
        super(Constants.ORDER_EMPTY);
    }
}
