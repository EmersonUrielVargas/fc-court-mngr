package com.foodcourt.court.infrastructure.out.jpa.mapper;

import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.infrastructure.out.jpa.entity.RestaurantEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IRestaurantEntityMapperTest {

    private final IRestaurantEntityMapper restaurantMapper = Mappers.getMapper(IRestaurantEntityMapper.class);

    @Test
    void shouldMapRestaurantToRestaurantEntity() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("El Sabor Casero");
        restaurant.setAddress("Calle 23 #123");
        restaurant.setOwnerId(10L);
        restaurant.setPhoneNumber("+573001234567");
        restaurant.setUrlLogo("https://example.com/logo.png");
        restaurant.setNit("9001234567");

        RestaurantEntity entity = restaurantMapper.toRestaurantEntity(restaurant);

        assertNotNull(entity, "the entity should not null");
        assertEquals(restaurant.getName(), entity.getNombre(), "name not match");
        assertEquals(restaurant.getAddress(), entity.getDireccion(), "address not match");
        assertEquals(restaurant.getOwnerId(), entity.getIdPropietario(), "ownerId not match");
        assertEquals(restaurant.getPhoneNumber(), entity.getTelefono(), "phoneNumber not match");
        assertEquals(restaurant.getUrlLogo(), entity.getUrlLogo(), "logo ULR not match");
        assertEquals(restaurant.getNit(), entity.getNit(), "nit not match");
    }

    @Test
    void shouldMapRestaurantEntityToRestaurant() {
        RestaurantEntity entity = new RestaurantEntity();
        entity.setNombre("La Esquina Gourmet12");
        entity.setDireccion("Avenida Siempre Viva 742");
        entity.setIdPropietario(202L);
        entity.setTelefono("+573109876543");
        entity.setUrlLogo("https://example.com/gourmet_logo.jpg");
        entity.setNit("800765432-1");

        Restaurant domainRestaurant = restaurantMapper.toRestaurant(entity);

        assertNotNull(domainRestaurant, "Object should not null");
        assertEquals(entity.getNombre(), domainRestaurant.getName(), "name not match");
        assertEquals(entity.getDireccion(), domainRestaurant.getAddress(), "address not match");
        assertEquals(entity.getIdPropietario(), domainRestaurant.getOwnerId(), "ownerId not match");
        assertEquals(entity.getTelefono(), domainRestaurant.getPhoneNumber(), "phoneNumber not match");
        assertEquals(entity.getUrlLogo(), domainRestaurant.getUrlLogo(), "logo ULR not match");
        assertEquals(entity.getNit(), domainRestaurant.getNit(), "nit not match");
    }

    @Test
    void shouldHandleNullRestaurantToRestaurantEntity() {
        Restaurant restaurant = null;

        RestaurantEntity entity = restaurantMapper.toRestaurantEntity(restaurant);

        assertNull(entity);
    }

    @Test
    void shouldHandleNullRestaurantEntityToRestaurant() {
        RestaurantEntity entity = null;

        Restaurant domainRestaurant = restaurantMapper.toRestaurant(entity);

        assertNull(domainRestaurant);
    }
}