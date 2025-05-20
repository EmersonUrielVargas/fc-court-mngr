package com.foodcourt.court.infrastructure.out.jpa.mapper;

import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.infrastructure.out.jpa.entity.CategoryEntity;
import com.foodcourt.court.infrastructure.out.jpa.entity.PlateEntity;
import com.foodcourt.court.infrastructure.out.jpa.entity.RestaurantEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IPlateEntityMapperTest {

    private final IPlateEntityMapper plateEntityMapper = Mappers.getMapper(IPlateEntityMapper.class);

    @Test
    void shouldMapPlateToPlateEntity() {
        Plate plate = new Plate();
        plate.setName("Hamburguesa Clásica");
        plate.setDescription("Carne de res, queso, lechuga, tomate");
        plate.setPrice(20000);
        plate.setUrlImage("https://example.com/hamburguesa.jpg");
        plate.setIsActive(true);
        plate.setCategoryId(1L);
        plate.setRestaurantId(10L);

        PlateEntity entity = plateEntityMapper.toPlateEntity(plate);

        assertNotNull(entity);
        assertEquals(plate.getName(), entity.getNombre());
        assertEquals(plate.getDescription(), entity.getDescripcion());
        assertEquals(plate.getPrice(), entity.getPrecio());
        assertEquals(plate.getUrlImage(), entity.getUrlImagen());
        assertEquals(plate.getIsActive(), entity.getActivo());

        assertNotNull(entity.getCategoria());
        assertEquals(plate.getCategoryId(), entity.getCategoria().getId());

        assertNotNull(entity.getRestaurante());
        assertEquals(plate.getRestaurantId(), entity.getRestaurante().getId());
    }

    @Test
    void shouldMapPlateEntityToPlate() {
        PlateEntity entity = new PlateEntity();
        entity.setNombre("Pizza Pepperoni");
        entity.setDescripcion("Pizza con pepperoni y extra queso");
        entity.setPrecio(2500);
        entity.setUrlImagen("https://example.com/pizza.jpg");
        entity.setActivo(true);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(2L);
        entity.setCategoria(categoryEntity);

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(11L);
        entity.setRestaurante(restaurantEntity);

        Plate domainPlate = plateEntityMapper.toPlate(entity);

        assertNotNull(domainPlate);
        assertEquals(entity.getNombre(), domainPlate.getName());
        assertEquals(entity.getDescripcion(), domainPlate.getDescription());
        assertEquals(entity.getPrecio(), domainPlate.getPrice());
        assertEquals(entity.getUrlImagen(), domainPlate.getUrlImage());
        assertEquals(entity.getActivo(), domainPlate.getIsActive());
        assertEquals(entity.getCategoria().getId(), domainPlate.getCategoryId());
        assertEquals(entity.getRestaurante().getId(), domainPlate.getRestaurantId());
    }

    @Test
    void mapIdToCategoriaEntityShouldReturnNull() {
        CategoryEntity categoryEntity = plateEntityMapper.mapIdToCategoriaEntity(null);

        assertNull(categoryEntity);
    }

    @Test
    void mapIdToCategoriaEntityShouldReturnCategoryEntity() {
        Long categoryId = 5L;
        CategoryEntity categoryEntity = plateEntityMapper.mapIdToCategoriaEntity(categoryId);

        assertNotNull(categoryEntity);
        assertEquals(categoryId, categoryEntity.getId());
        assertNull(categoryEntity.getNombre());
        assertNull(categoryEntity.getDescripcion());
    }

    @Test
    void mapIdToRestaurantEntity_whenRestaurantIdIsNull_shouldReturnNull() {
        RestaurantEntity restaurantEntity = plateEntityMapper.mapIdToRestaurantEntity(null);

        assertNull(restaurantEntity);
    }

    @Test
    void mapIdToRestaurantEntityShouldReturnRestaurantEntity() {
        Long restaurantId = 15L;
        RestaurantEntity restaurantEntity = plateEntityMapper.mapIdToRestaurantEntity(restaurantId);

        assertNotNull(restaurantEntity);
        assertEquals(restaurantId, restaurantEntity.getId());
    }
}