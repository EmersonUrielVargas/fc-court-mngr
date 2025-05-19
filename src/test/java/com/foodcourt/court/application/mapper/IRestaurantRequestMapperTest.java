package com.foodcourt.court.application.mapper;

import com.foodcourt.court.application.dto.request.RestaurantRequestDto;
import com.foodcourt.court.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IRestaurantRequestMapperTest {

    private IRestaurantRequestMapper restaurantRequestMapper = Mappers.getMapper(IRestaurantRequestMapper.class);

    @Test
    void toRestaurant() {
        RestaurantRequestDto restaurantRq =  new RestaurantRequestDto();
        restaurantRq.setName("El gran Sabor");
        restaurantRq.setNit("1223435542");
        restaurantRq.setAddress("Calle 22 #43-55");
        restaurantRq.setOwnerId(10L);
        restaurantRq.setPhoneNumber("+571238000612");
        restaurantRq.setUrlLogo("www.logo.com");

        Restaurant restaurant = restaurantRequestMapper.toRestaurant(restaurantRq);

        assertAll("validation mapping RestaurantRequestDto into Restaurant",
                ()->assertEquals(restaurantRq.getName(), restaurant.getName()),
                ()->assertEquals(restaurantRq.getNit(), restaurant.getNit()),
                ()->assertEquals(restaurantRq.getAddress(), restaurant.getAddress()),
                ()->assertEquals(restaurantRq.getPhoneNumber(), restaurant.getPhoneNumber()),
                ()->assertEquals(restaurantRq.getOwnerId(), restaurant.getOwnerId()),
                ()->assertEquals(restaurantRq.getUrlLogo(), restaurant.getUrlLogo())
        );

    }
}