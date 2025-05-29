package com.foodcourt.court.infrastructure.out.jpa.mapper;

import com.foodcourt.court.domain.DataDomainFactory;
import com.foodcourt.court.domain.model.Category;
import com.foodcourt.court.infrastructure.out.DataEntityJpaFactory;
import com.foodcourt.court.infrastructure.out.jpa.entity.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ICategoryEntityMapperTest {

    private final ICategoryEntityMapper categoryEntityMapper = Mappers.getMapper(ICategoryEntityMapper.class);

    @Test
    void shouldMapCategoryToCategoryEntity() {
        Category category = DataDomainFactory.createCategory();
        CategoryEntity entity = categoryEntityMapper.toCategoryEntity(category);

        assertNotNull(entity);
        assertEquals(category.getName(), entity.getNombre());
        assertEquals(category.getDescription(), entity.getDescripcion());
        assertEquals(category.getId(), entity.getId());
    }

    @Test
    void shouldMapCategoryEntityToCategory() {
        CategoryEntity entity = DataEntityJpaFactory.createCategoryEntity();

        Category category = categoryEntityMapper.toCategory(entity);

        assertNotNull(entity);
        assertEquals(category.getName(), entity.getNombre());
        assertEquals(category.getDescription(), entity.getDescripcion());
        assertEquals(category.getId(), entity.getId());
    }
}