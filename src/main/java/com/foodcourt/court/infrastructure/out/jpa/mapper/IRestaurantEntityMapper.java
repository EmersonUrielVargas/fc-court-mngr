package com.foodcourt.court.infrastructure.out.jpa.mapper;

import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.infrastructure.out.jpa.entity.RestaurantEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantEntityMapper {


    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "address", target = "direccion")
    @Mapping(source = "ownerId", target = "idPropietario")
    @Mapping(source = "phoneNumber", target = "telefono")
    @Mapping(source = "urlLogo", target = "urlLogo")
    @Mapping(source = "nit", target = "nit")
    RestaurantEntity toRestaurantEntity(Restaurant restaurant);

    @InheritInverseConfiguration
    Restaurant toRestaurant(RestaurantEntity restaurantEntity);
    List<Restaurant> toRestaurant(List<RestaurantEntity> restaurantEntity);
}
