package com.foodcourt.court.domain;

import com.foodcourt.court.domain.enums.OrderStatus;
import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.domain.model.*;
import com.foodcourt.court.domain.utilities.CustomPage;
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

    public static <T> CustomPage<T> createEmptyCustomPage(){
        CustomPage<T> page = new CustomPage<>();
        page.setCurrentPage(DataConstants.DEFAULT_CUSTOM_PAGE_CURRENT_PAGE);
        page.setIsLastPage(DataConstants.DEFAULT_CUSTOM_PAGE_IS_LAST_PAGE);
        page.setPageSize(DataConstants.DEFAULT_CUSTOM_PAGE_SIZE_PAGE);
        page.setTotalItems(DataConstants.DEFAULT_CUSTOM_PAGE_TOTAL_ITEMS);
        page.setTotalPages(DataConstants.DEFAULT_CUSTOM_PAGE_TOTAL_PAGES);
        return page;
    }


    public static <T> CustomPage<T> createCustomPage(List<T> items){
        CustomPage<T> page = new CustomPage<>();
        page.setCurrentPage(DataConstants.DEFAULT_CUSTOM_PAGE_CURRENT_PAGE);
        page.setIsLastPage(DataConstants.DEFAULT_CUSTOM_PAGE_IS_LAST_PAGE);
        page.setPageSize(DataConstants.DEFAULT_CUSTOM_PAGE_SIZE_PAGE);
        page.setTotalItems(DataConstants.DEFAULT_CUSTOM_PAGE_TOTAL_ITEMS);
        page.setTotalPages(DataConstants.DEFAULT_CUSTOM_PAGE_TOTAL_PAGES);
        page.setData(items);
        return page;
    }

    public static User createUserClient(){
        return User.builder()
                .email(DataConstants.DEFAULT_USER_CLIENT_EMAIL)
                .phoneNumber(DataConstants.DEFAULT_USER_PHONE_NUMBER)
                .name(DataConstants.DEFAULT_USER_NAME)
                .id(DataConstants.DEFAULT_USER_ID)
                .rol(UserRole.CLIENT)
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

    public static Order createOrderBd(){
        return Order.builder()
                .id(DataConstants.DEFAULT_ORDER_ID)
                .clientId(DataConstants.DEFAULT_ORDER_CLIENT_ID)
                .status(OrderStatus.PENDING)
                .restaurantId(DataConstants.DEFAULT_RESTAURANT_ID)
                .orderPlates(createListOrderPlate())
                .build();
    }

    public static AssignmentEmployee createAssignmentEmployee() {
        return AssignmentEmployee.builder()
                .employeeId(DataConstants.DEFAULT_USER_ID)
                .restaurantId(DataConstants.DEFAULT_RESTAURANT_ID)
                .build();
    }
}
