package com.foodcourt.court.infrastructure.out.jpa.mapper;

import com.foodcourt.court.domain.model.Category;
import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.infrastructure.out.jpa.entity.CategoryEntity;
import com.foodcourt.court.infrastructure.out.jpa.entity.RestaurantEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ICategoryEntityMapper {

    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "description", target = "descripcion")
    CategoryEntity toCategoryEntity(Category category);

    @InheritInverseConfiguration
    Category toCategory(CategoryEntity categoryEntity);
}
