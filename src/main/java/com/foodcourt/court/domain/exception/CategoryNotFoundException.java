package com.foodcourt.court.domain.exception;

import com.foodcourt.court.domain.constants.Constants;

public class CategoryNotFoundException extends DomainException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
    public CategoryNotFoundException() {
        super(Constants.CATEGORY_NO_FOUND);
    }
}
