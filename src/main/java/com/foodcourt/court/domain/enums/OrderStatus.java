package com.foodcourt.court.domain.enums;

import com.foodcourt.court.domain.constants.Constants;
import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING(Constants.ORDER_STATUS_PENDING),
    CANCELED(Constants.ORDER_STATUS_CANCELED),
    IN_PREPARATION(Constants.ORDER_STATUS_IN_PREPARATION),
    PREPARED(Constants.ORDER_STATUS_PREPARED),
    DELIVERED(Constants.ORDER_STATUS_DELIVERED);

    private final String message;

    OrderStatus(String message) {
        this.message = message;
    }

}
