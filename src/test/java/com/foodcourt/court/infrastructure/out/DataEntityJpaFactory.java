package com.foodcourt.court.infrastructure.out;

import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.infrastructure.out.jpa.entity.CategoryEntity;
import com.foodcourt.court.shared.DataConstants;

public class DataEntityJpaFactory {
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

    public static CategoryEntity createCategoryEntity(){
        CategoryEntity entity = new CategoryEntity();
        entity.setId(12L);
        entity.setNombre("Postres");
        entity.setDescripcion("Postres para acompañar");
        return entity;
    }
}
