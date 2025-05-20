package com.foodcourt.court.infrastructure.out.jpa.mapper;

import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.infrastructure.out.jpa.entity.CategoryEntity;
import com.foodcourt.court.infrastructure.out.jpa.entity.PlateEntity;
import com.foodcourt.court.infrastructure.out.jpa.entity.RestaurantEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IPlateEntityMapper {


    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "description", target = "descripcion")
    @Mapping(source = "price", target = "precio")
    @Mapping(source = "urlImage", target = "urlImagen")
    @Mapping(source = "isActive", target = "activo")
    @Mapping(source = "categoryId", target = "categoria", qualifiedByName = "mapIdToCategoriaEntity")
    @Mapping(source = "restaurantId", target = "restaurante", qualifiedByName = "mapIdToRestaurantEntity")
    PlateEntity toPlateEntity(Plate plate);

    @InheritInverseConfiguration
    @Mapping(source = "categoria.id", target = "categoryId")
    @Mapping(source = "restaurante.id", target = "restaurantId")
    Plate toPlate(PlateEntity plateEntity);

    @Named("mapIdToCategoriaEntity")
    default CategoryEntity mapIdToCategoriaEntity(Long categoryId){
        if (categoryId == null) return null;
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryId);
        return categoryEntity;
    }

    @Named("mapIdToRestaurantEntity")
    default RestaurantEntity mapIdToRestaurantEntity(Long restaurantId){
        if (restaurantId == null) return null;
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(restaurantId);
        return restaurantEntity;
    }
}
