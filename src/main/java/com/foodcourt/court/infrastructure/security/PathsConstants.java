package com.foodcourt.court.infrastructure.security;

import com.foodcourt.court.domain.constants.Constants;

public class PathsConstants {
    private PathsConstants() {
        throw new IllegalStateException(Constants.ERROR_INSTANCE_UTILITY_CLASS);
    }


    protected static final String[] PATHS_ALL_ALLOW = {
            "/public/**", "/swagger-ui/**", "/swagger-ui.**", "/v3/api-docs/**"
    };

    protected static final String[] PATHS_GET_CLIENT = {
            "/v1/restaurant", "/v1/restaurant/*/plates", "/v1/order/**"
    };

    protected static final String[] PATHS_CLIENT = {
            "/v1/order/cancel"
    };

    protected static final String[] PATHS_EMPLOYEE = {
            "/v1/restaurant/*/orders", "/v1/order/status"
    };

    protected static final String[] PATHS_OWNER = {
            "/v1/plate/**", "/v1/restaurant/assignment", "/v1/restaurant/employee/id", "/v1/restaurant/order/id"
    };

    protected static final String[] PATHS_ADMIN = {
            "/v1/restaurant/**"
    };
}
