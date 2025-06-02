package com.foodcourt.court.domain.exception;


public class ActionNotAllowedException extends DomainException {
    public ActionNotAllowedException(String message) {
        super(message);
    }
}
