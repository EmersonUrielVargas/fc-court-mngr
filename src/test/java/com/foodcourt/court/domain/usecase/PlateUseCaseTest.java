package com.foodcourt.court.domain.usecase;

import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.exception.DomainException;
import com.foodcourt.court.domain.model.Category;
import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.spi.ICategoryPersistencePort;
import com.foodcourt.court.domain.spi.IPlatePersistencePort;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlateUseCaseTest {

    @Mock
    private IPlatePersistencePort platePersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @Captor
    private ArgumentCaptor<Plate> plateArgumentCaptor;

    @InjectMocks
    private PlateUseCases plateUseCases;

    @Test
    void createPlate() {
        Long restaurantId = 10L;
        Long categoryId = 5L;
        Plate plate = Plate.builder()
                .name("Pizza Margarita Especial")
                .restaurantId(restaurantId)
                .price(12000)
                .categoryId(categoryId)
                .description("Pizza vegetariana con pepinillos")
                .build();

        when(restaurantPersistencePort.getById(restaurantId))
                .thenReturn(Optional.of(Restaurant.builder().id(restaurantId).build()));

        when(categoryPersistencePort.getCategoryById(categoryId))
                .thenReturn(Optional.of(Category.builder().id(categoryId).build()));
        plateUseCases.create(plate);
        verify(platePersistencePort).createPlate(plate);
    }

    @Test
    void createRestaurantFailCategoryNotFound() {
        Long restaurantId = 10L;
        Long categoryId = 5L;
        Plate plate = Plate.builder()
                .name("Pizza Margarita Especial")
                .restaurantId(restaurantId)
                .price(12000)
                .categoryId(categoryId)
                .description("Pizza vegetariana con pepinillos")
                .build();

        when(categoryPersistencePort.getCategoryById(categoryId))
                .thenReturn(Optional.empty());

        DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.create(plate));
        assertEquals(Constants.CATEGORY_NO_FOUND, exception.getMessage());
    }

    @Test
    void createRestaurantFailRestaurantNotFound() {
        Long restaurantId = 10L;
        Long categoryId = 5L;
        Plate plate = Plate.builder()
                .name("Pizza Margarita Especial")
                .restaurantId(restaurantId)
                .price(12000)
                .categoryId(categoryId)
                .description("Pizza vegetariana con pepinillos")
                .build();

        when(restaurantPersistencePort.getById(restaurantId))
                .thenReturn(Optional.empty());

        when(categoryPersistencePort.getCategoryById(categoryId))
                .thenReturn(Optional.of(Category.builder().id(categoryId).build()));

        DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.create(plate));
        assertEquals(Constants.RESTAURANT_NO_FOUND, exception.getMessage());
    }

    @Test
    void createRestaurantFailInvalidPrice() {
        Long restaurantId = 10L;
        Long categoryId = 5L;
        Plate plate = Plate.builder()
                .name("Pizza Margarita Especial")
                .restaurantId(restaurantId)
                .price(-12000)
                .categoryId(categoryId)
                .description("Pizza vegetariana con pepinillos")
                .build();

        DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.create(plate));
        assertEquals(Constants.PRICE_NOT_ALLOWED, exception.getMessage());
    }

    @Test
    void updatePlateAllFieldsSuccessful() {
        Long plateId = 5L;
        Plate plate = Plate.builder()
                .id(plateId)
                .price(12560)
                .description("Esta no es una pizza!!")
                .build();

        Plate existingPlate = Plate.builder()
                .id(plateId)
                .price(10990)
                .description("Pizza vegetariana con pepinillos")
                .build();

        when(platePersistencePort.getByID(plateId))
                .thenReturn(Optional.of(existingPlate));

        plateUseCases.update(plate);
        verify(platePersistencePort).updatePlate(plateArgumentCaptor.capture());
        Plate plateUpdated = plateArgumentCaptor.getValue();
        assertEquals(plate.getPrice(), plateUpdated.getPrice());
        assertEquals(plate.getDescription(), plateUpdated.getDescription());
    }

    @Test
    void updatePlateOneFieldSuccessful() {
        Long plateId = 5L;
        Plate plate = Plate.builder()
                .id(plateId)
                .price(12560)
                .build();

        Plate existingPlate = Plate.builder()
                .id(plateId)
                .price(10990)
                .description("Pizza vegetariana con pepinillos")
                .build();

        when(platePersistencePort.getByID(plateId))
                .thenReturn(Optional.of(existingPlate));

        plateUseCases.update(plate);

        verify(platePersistencePort).updatePlate(plateArgumentCaptor.capture());
        Plate plateUpdated = plateArgumentCaptor.getValue();
        assertEquals(plate.getPrice(), plateUpdated.getPrice());
        assertEquals(existingPlate.getDescription(), plateUpdated.getDescription());
    }

    @Test
    void updatePlatePriceFail() {
        Long plateId = 5L;
        Plate plate = Plate.builder()
                .id(plateId)
                .price(-1000)
                .build();

        Plate existingPlate = Plate.builder()
                .id(plateId)
                .price(10990)
                .description("Pizza vegetariana con pepinillos")
                .build();

        when(platePersistencePort.getByID(plateId))
                .thenReturn(Optional.of(existingPlate));

        DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.update(plate));
        assertEquals(Constants.PRICE_NOT_ALLOWED, exception.getMessage());

    }
}