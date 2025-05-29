package com.foodcourt.court.domain;

import com.foodcourt.court.domain.model.*;
import com.foodcourt.court.shared.DataConstants;

import java.util.ArrayList;
import java.util.List;

public class DataDomainFactory {
    public static Plate createPlate(){
        return Plate.builder()
                .id(DataConstants.DEFAULT_PLATE_ID)
                .name(DataConstants.DEFAULT_PLATE_NAME)
                .restaurantId(DataConstants.DEFAULT_RESTAURANT_ID)
                .price(DataConstants.DEFAULT_PLATE_PRICE)
                .categoryId(DataConstants.DEFAULT_CATEGORY_ID)
                .description(DataConstants.DEFAULT_PLATE_DESCRIPTION)
                .build();
    }

    public static Restaurant createRestaurant(){
        return Restaurant.builder()
                .id(DataConstants.DEFAULT_RESTAURANT_ID)
                .name(DataConstants.DEFAULT_RESTAURANT_NAME)
                .nit(DataConstants.DEFAULT_RESTAURANT_NIT)
                .address(DataConstants.DEFAULT_RESTAURANT_ADDRESS)
                .phoneNumber(DataConstants.DEFAULT_RESTAURANT_PHONE_NUMBER)
                .ownerId(DataConstants.DEFAULT_OWNER_ID)
                .urlLogo(DataConstants.DEFAULT_RESTAURANT_URL_LOGO)
                .build();
    }

    public static Category createCategory(){
        return Category.builder()
                .id(DataConstants.DEFAULT_CATEGORY_ID)
                .name(DataConstants.DEFAULT_CATEGORY_NAME)
                .description(DataConstants.DEFAULT_CATEGORY_DESCRIPTION)
                .build();
    }

    public static OrderPlate createOrderPlate(){
        return OrderPlate.builder()
                .orderId(DataConstants.DEFAULT_ORDER_PLATE_ORDER_ID)
                .plateId(DataConstants.DEFAULT_ORDER_PLATE_PLATE_ID)
                .quantity(DataConstants.DEFAULT_QUANTITY)
                .build();
    }

    public static List<OrderPlate> createListOrderPlate(){
        List<OrderPlate> orderPlates = new ArrayList<>();
        orderPlates.add(createOrderPlate());
        return orderPlates;
    }

    public static Order createOrder(){
        return Order.builder()
                .restaurantId(DataConstants.DEFAULT_RESTAURANT_ID)
                .orderPlates(createListOrderPlate())
                .build();
    }
}
