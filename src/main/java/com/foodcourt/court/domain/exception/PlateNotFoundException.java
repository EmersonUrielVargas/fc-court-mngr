package com.foodcourt.court.domain.exception;

import com.foodcourt.court.domain.constants.Constants;

public class PlateNotFoundException extends DomainException {
    public PlateNotFoundException(String message) {
        super(message);
    }
    public PlateNotFoundException() {
        super(Constants.PLATE_NO_FOUND);
    }
}
