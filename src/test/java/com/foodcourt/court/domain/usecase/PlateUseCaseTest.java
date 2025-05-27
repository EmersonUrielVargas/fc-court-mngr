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
        Long ownerId = 10L;
        Plate plate = Plate.builder()
                .name("Pizza Margarita Especial")
                .restaurantId(restaurantId)
                .price(12000)
                .categoryId(categoryId)
                .description("Pizza vegetariana con pepinillos")
                .build();

        when(restaurantPersistencePort.getById(restaurantId))
                .thenReturn(Optional.of(Restaurant.builder().id(restaurantId).ownerId(ownerId).build()));

        when(categoryPersistencePort.getCategoryById(categoryId))
                .thenReturn(Optional.of(Category.builder().id(categoryId).build()));
        plateUseCases.create(plate, ownerId);
        verify(platePersistencePort).createPlate(plate);
    }

    @Test
    void createRestaurantFailCategoryNotFound() {
        Long restaurantId = 10L;
        Long categoryId = 5L;
        Long ownerId = 10L;

        Plate plate = Plate.builder()
                .name("Pizza Margarita Especial")
                .restaurantId(restaurantId)
                .price(12000)
                .categoryId(categoryId)
                .description("Pizza vegetariana con pepinillos")
                .build();

        when(categoryPersistencePort.getCategoryById(categoryId))
                .thenReturn(Optional.empty());

        DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.create(plate, ownerId));
        assertEquals(Constants.CATEGORY_NO_FOUND, exception.getMessage());
    }

    @Test
    void createRestaurantFailRestaurantNotFound() {
        Long restaurantId = 10L;
        Long categoryId = 5L;
        Long ownerId = 10L;

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

        DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.create(plate, ownerId));
        assertEquals(Constants.RESTAURANT_NO_FOUND, exception.getMessage());
    }

    @Test
    void createRestaurantFailOwnerNotMatch() {
        Long restaurantId = 10L;
        Long categoryId = 5L;
        Long ownerId = 10L;

        Plate plate = Plate.builder()
                .name("Pizza Margarita Especial")
                .restaurantId(restaurantId)
                .price(12000)
                .categoryId(categoryId)
                .description("Pizza vegetariana con pepinillos")
                .build();

        when(restaurantPersistencePort.getById(restaurantId))
                .thenReturn(Optional.of(Restaurant.builder().id(restaurantId).ownerId(categoryId).build()));

        when(categoryPersistencePort.getCategoryById(categoryId))
                .thenReturn(Optional.of(Category.builder().id(categoryId).build()));

        DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.create(plate, ownerId));
        assertEquals(Constants.OWNER_NOT_ALLOWED, exception.getMessage());
    }

    @Test
    void createRestaurantFailInvalidPrice() {
        Long restaurantId = 10L;
        Long categoryId = 5L;
        Long ownerId = 10L;

        Plate plate = Plate.builder()
                .name("Pizza Margarita Especial")
                .restaurantId(restaurantId)
                .price(-12000)
                .categoryId(categoryId)
                .description("Pizza vegetariana con pepinillos")
                .build();

        DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.create(plate, ownerId));
        assertEquals(Constants.PRICE_NOT_ALLOWED, exception.getMessage());
    }

    @Test
    void updatePlateAllFieldsSuccessful() {
        Long plateId = 5L;
        Long ownerId = 10L;
        Long restaurantId = 10L;


        Plate plate = Plate.builder()
                .id(plateId)
                .price(12560)
                .description("Esta no es una pizza!!")
                .build();

        Plate existingPlate = Plate.builder()
                .id(plateId)
                .price(10990)
                .restaurantId(restaurantId)
                .description("Pizza vegetariana con pepinillos")
                .build();

        when(platePersistencePort.getByID(plateId))
                .thenReturn(Optional.of(existingPlate));

        when(restaurantPersistencePort.getById(restaurantId))
                .thenReturn(Optional.of(Restaurant.builder().id(restaurantId).ownerId(ownerId).build()));

        plateUseCases.update(plate, ownerId);
        verify(platePersistencePort).upsertPlate(plateArgumentCaptor.capture());
        Plate plateUpdated = plateArgumentCaptor.getValue();
        assertEquals(plate.getPrice(), plateUpdated.getPrice());
        assertEquals(plate.getDescription(), plateUpdated.getDescription());
    }

    @Test
    void updatePlateOneFieldSuccessful() {
        Long plateId = 5L;
        Long ownerId = 10L;
        Long restaurantId = 10L;

        Plate plate = Plate.builder()
                .id(plateId)
                .price(12560)
                .build();

        Plate existingPlate = Plate.builder()
                .id(plateId)
                .price(10990)
                .restaurantId(restaurantId)
                .description("Pizza vegetariana con pepinillos")
                .build();

        when(platePersistencePort.getByID(plateId))
                .thenReturn(Optional.of(existingPlate));
        when(restaurantPersistencePort.getById(restaurantId))
                .thenReturn(Optional.of(Restaurant.builder().id(restaurantId).ownerId(ownerId).build()));

        plateUseCases.update(plate, ownerId);

        verify(platePersistencePort).upsertPlate(plateArgumentCaptor.capture());
        Plate plateUpdated = plateArgumentCaptor.getValue();
        assertEquals(plate.getPrice(), plateUpdated.getPrice());
        assertEquals(existingPlate.getDescription(), plateUpdated.getDescription());
    }

    @Test
    void updatePlatePriceFail() {
        Long plateId = 5L;
        Long ownerId = 10L;
        Long restaurantId = 10L;

        Plate plate = Plate.builder()
                .id(plateId)
                .price(-1000)
                .build();

        Plate existingPlate = Plate.builder()
                .id(plateId)
                .price(10990)
                .restaurantId(restaurantId)
                .description("Pizza vegetariana con pepinillos")
                .build();

        when(platePersistencePort.getByID(plateId))
                .thenReturn(Optional.of(existingPlate));

        when(restaurantPersistencePort.getById(restaurantId))
                .thenReturn(Optional.of(Restaurant.builder().id(restaurantId).ownerId(ownerId).build()));

        DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.update(plate, ownerId));
        assertEquals(Constants.PRICE_NOT_ALLOWED, exception.getMessage());

    }

    @Test
    void updatePlateFailOwnerIdNotAllowed() {
        Long plateId = 5L;
        Long ownerId = 10L;
        Long restaurantId = 10L;

        Plate plate = Plate.builder()
                .id(plateId)
                .price(-1000)
                .build();

        Plate existingPlate = Plate.builder()
                .id(plateId)
                .price(10990)
                .restaurantId(restaurantId)
                .description("Pizza vegetariana con pepinillos")
                .build();

        when(platePersistencePort.getByID(plateId))
                .thenReturn(Optional.of(existingPlate));

        when(restaurantPersistencePort.getById(restaurantId))
                .thenReturn(Optional.of(Restaurant.builder().id(restaurantId).ownerId(plateId).build()));

        DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.update(plate, ownerId));
        assertEquals(Constants.OWNER_NOT_ALLOWED, exception.getMessage());

    }

    @Test
    void updatePlateFailRestaurantNotFound() {
        Long plateId = 5L;
        Long ownerId = 10L;
        Long restaurantId = 10L;

        Plate plate = Plate.builder()
                .id(plateId)
                .price(-1000)
                .build();

        Plate existingPlate = Plate.builder()
                .id(plateId)
                .price(10990)
                .restaurantId(restaurantId)
                .description("Pizza vegetariana con pepinillos")
                .build();

        when(platePersistencePort.getByID(plateId))
                .thenReturn(Optional.of(existingPlate));

        when(restaurantPersistencePort.getById(restaurantId))
                .thenReturn(Optional.empty());

        DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.update(plate, ownerId));
        assertEquals(Constants.RESTAURANT_NO_FOUND, exception.getMessage());

    }

    @Test
    void setStatusPlateSuccessful() {
        Long plateId = 5L;
        Long ownerId = 10L;
        Long restaurantId = 10L;

        Plate plate = Plate.builder()
                .id(plateId)
                .isActive(false)
                .build();

        Plate existingPlate = Plate.builder()
                .id(plateId)
                .isActive(true)
                .restaurantId(restaurantId)
                .build();
        Restaurant existingRestaurant = Restaurant.builder().
                id(restaurantId).
                ownerId(ownerId)
                .build();

        when(platePersistencePort.getByID(plateId))
                .thenReturn(Optional.of(existingPlate));

        when(restaurantPersistencePort.getById(restaurantId))
                .thenReturn(Optional.of(existingRestaurant));

        plateUseCases.setActive(plate, ownerId);
        verify(platePersistencePort).upsertPlate(plateArgumentCaptor.capture());
        Plate plateUpdated = plateArgumentCaptor.getValue();
        assertEquals(plate.getIsActive(), plateUpdated.getIsActive());
    }
}