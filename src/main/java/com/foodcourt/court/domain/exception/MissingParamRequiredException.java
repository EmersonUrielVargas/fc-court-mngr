package com.foodcourt.court.domain.exception;

import com.foodcourt.court.domain.constants.Constants;

public class MissingParamRequiredException extends DomainException {
    public MissingParamRequiredException(String message) {
        super(message);
    }
    public MissingParamRequiredException() {
        super(Constants.PARAM_REQUIRED_NOT_FOUND);
    }
}
