package com.foodcourt.court.infrastructure.shared;

import static com.foodcourt.court.domain.constants.Constants.ERROR_INSTANCE_UTILITY_CLASS;

public class GeneralConstants {

    private GeneralConstants() {
        throw new IllegalStateException(ERROR_INSTANCE_UTILITY_CLASS);
    }
    public static final String STATUS_CODE_CREATED= "201";
    public static final String STATUS_CODE_CONFLICT= "409";
    public static final String STATUS_CODE_BAD_REQUEST= "400";
    public static final String STATUS_CODE_OK= "200";

    public static final String MEDIA_TYPE_JSON= "application/json";


    public static final String SUMMARY_CREATE_RESTAURANT = "Create a new restaurant in food court";
    public static final String SUMMARY_RESPONSE_CREATED_RESTAURANT = "restaurant created successful";
    public static final String SUMMARY_RESPONSE_CONFLICT_RESTAURANT = "restaurant already exists or information invalid";

    public static final String SUMMARY_CREATE_PLATE = "Create a new plate in a restaurant";
    public static final String SUMMARY_RESPONSE_CREATED_PLATE = "Plate created successful";
    public static final String SUMMARY_RESPONSE_CONFLICT_PLATE = "Information plate invalid";

    public static final String SUMMARY_UPDATE_PLATE = "update a existent plate in a restaurant";
    public static final String SUMMARY_RESPONSE_OK_UPDATE_PLATE = "Plate update successful";

    public static final String SUMMARY_SET_ACTIVE_PLATE = "update status to enable or disable a plate in a restaurant";
    public static final String SUMMARY_RESPONSE_OK_SET_ACTIVE_PLATE = "Plate status update successful";

    public static final String SUMMARY_GET_RESTAURANT = "Get a list of restaurants in food court, filtering by pagination and sorting by name ascending";
    public static final String SUMMARY_RESPONSE_OK_GET_RESTAURANT = "List of restaurants with filtering pagination applied";
    public static final String SUMMARY_RESPONSE_BAD_REQUEST_GET_RESTAURANT = "Invalid params value";

    /*Repository*/
    public static final String FIELD_NAME_GET_RESTAURANTS = "nombre";


}
