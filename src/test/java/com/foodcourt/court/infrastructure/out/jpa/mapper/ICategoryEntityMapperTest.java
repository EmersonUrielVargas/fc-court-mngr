package com.foodcourt.court.infrastructure.out.jpa.mapper;

import com.foodcourt.court.domain.model.Category;
import com.foodcourt.court.infrastructure.out.jpa.entity.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ICategoryEntityMapperTest {

    private final ICategoryEntityMapper categoryEntityMapper = Mappers.getMapper(ICategoryEntityMapper.class);

    @Test
    void shouldMapCategoryToCategoryEntity() {
        Category category = Category
                .builder()
                .id(12L)
                .description("Postres para acompañar")
                .name("Postres")
                .build();

        CategoryEntity entity = categoryEntityMapper.toCategoryEntity(category);

        assertNotNull(entity, "the entity should not null");
        assertEquals(category.getName(), entity.getNombre(), "name not match");
        assertEquals(category.getDescription(), entity.getDescripcion(), "description not match");
        assertEquals(category.getId(), entity.getId(), "id not match");
    }

    @Test
    void shouldMapCategoryEntityToCategory() {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(12L);
        entity.setNombre("Postres");
        entity.setDescripcion("Postres para acompañar");

        Category category = categoryEntityMapper.toCategory(entity);

        assertNotNull(entity, "the entity should not null");
        assertEquals(category.getName(), entity.getNombre(), "name not match");
        assertEquals(category.getDescription(), entity.getDescripcion(), "description not match");
        assertEquals(category.getId(), entity.getId(), "id not match");
    }
}