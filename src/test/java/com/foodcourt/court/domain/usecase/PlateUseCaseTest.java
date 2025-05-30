package com.foodcourt.court.domain.usecase;

import com.foodcourt.court.domain.DataDomainFactory;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.exception.DomainException;
import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.spi.IAuthenticationPort;
import com.foodcourt.court.domain.spi.ICategoryPersistencePort;
import com.foodcourt.court.domain.spi.IPlatePersistencePort;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import com.foodcourt.court.shared.DataConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlateUseCaseTest {

    @Mock
    private IPlatePersistencePort platePersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @Mock
    private IAuthenticationPort authenticationPort;

    @Captor
    private ArgumentCaptor<Plate> plateArgumentCaptor;

    @InjectMocks
    private PlateUseCases plateUseCases;

    @Nested
    @DisplayName("createPlate")
    class createPlateTests{
        @Test
        void createPlateSuccessful() {
            Plate plate = DataDomainFactory.createPlate();

            when(restaurantPersistencePort.getById(DataConstants.DEFAULT_RESTAURANT_ID))
                    .thenReturn(Optional.of(DataDomainFactory.createRestaurant()));

            when(authenticationPort.getAuthenticateUserId())
                    .thenReturn(DataConstants.DEFAULT_OWNER_ID);

            when(categoryPersistencePort.getCategoryById(DataConstants.DEFAULT_CATEGORY_ID))
                    .thenReturn(Optional.of(DataDomainFactory.createCategory()));
            plateUseCases.create(plate);
            verify(platePersistencePort).upsertPlate(plate);
        }

        @Test
        void createPlateFailCategoryNotFound() {
            Plate plate = DataDomainFactory.createPlate();

            when(categoryPersistencePort.getCategoryById(DataConstants.DEFAULT_CATEGORY_ID))
                    .thenReturn(Optional.empty());

            DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.create(plate));
            assertEquals(Constants.CATEGORY_NO_FOUND, exception.getMessage());
        }

        @Test
        void createPlateFailRestaurantNotFound() {
            Plate plate = DataDomainFactory.createPlate();

            when(restaurantPersistencePort.getById(DataConstants.DEFAULT_RESTAURANT_ID))
                    .thenReturn(Optional.empty());

            when(categoryPersistencePort.getCategoryById(DataConstants.DEFAULT_CATEGORY_ID))
                    .thenReturn(Optional.of(DataDomainFactory.createCategory()));

            when(authenticationPort.getAuthenticateUserId())
                    .thenReturn(DataConstants.DEFAULT_OWNER_ID);

            DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.create(plate));
            assertEquals(Constants.RESTAURANT_NO_FOUND, exception.getMessage());
        }

        @Test
        void createPlateFailOwnerNotMatch() {
            Long ownerId = 10L;
            Plate plate = DataDomainFactory.createPlate();

            when(restaurantPersistencePort.getById(DataConstants.DEFAULT_RESTAURANT_ID))
                    .thenReturn(Optional.of(DataDomainFactory.createRestaurant()));

            when(categoryPersistencePort.getCategoryById(DataConstants.DEFAULT_CATEGORY_ID))
                    .thenReturn(Optional.of(DataDomainFactory.createCategory()));

            when(authenticationPort.getAuthenticateUserId())
                    .thenReturn(ownerId);

            DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.create(plate));
            assertEquals(Constants.OWNER_NOT_ALLOWED, exception.getMessage());
        }

        @Test
        void createPlateFailInvalidPrice() {
            Plate plate = DataDomainFactory.createPlate();
            plate.setPrice(-12300);

            DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.create(plate));
            assertEquals(Constants.PRICE_NOT_ALLOWED, exception.getMessage());
        }
    }

    @Nested
    @DisplayName("updatePlate")
    class updatePlateTests{
        @Test
        void updatePlateAllFieldsSuccessful() {
            Plate plate = DataDomainFactory.createPlate();
            plate.setPrice(54000);
            plate.setDescription("Esta no es una descripcion");

            Plate existingPlate = DataDomainFactory.createPlate();

            when(platePersistencePort.getByID(DataConstants.DEFAULT_PLATE_ID))
                    .thenReturn(Optional.of(existingPlate));

            when(restaurantPersistencePort.getById(DataConstants.DEFAULT_RESTAURANT_ID))
                    .thenReturn(Optional.of(DataDomainFactory.createRestaurant()));

            when(authenticationPort.getAuthenticateUserId())
                    .thenReturn(DataConstants.DEFAULT_OWNER_ID);

            plateUseCases.update(plate);
            verify(platePersistencePort).upsertPlate(plateArgumentCaptor.capture());
            Plate plateUpdated = plateArgumentCaptor.getValue();
            assertEquals(plate.getPrice(), plateUpdated.getPrice());
            assertEquals(plate.getDescription(), plateUpdated.getDescription());
        }

        @Test
        void updatePlateOneFieldSuccessful() {
            Plate plate = DataDomainFactory.createPlate();
            plate.setPrice(54000);

            Plate existingPlate = DataDomainFactory.createPlate();

            when(platePersistencePort.getByID(DataConstants.DEFAULT_PLATE_ID))
                    .thenReturn(Optional.of(existingPlate));

            when(restaurantPersistencePort.getById(DataConstants.DEFAULT_RESTAURANT_ID))
                    .thenReturn(Optional.of(DataDomainFactory.createRestaurant()));

            when(authenticationPort.getAuthenticateUserId())
                    .thenReturn(DataConstants.DEFAULT_OWNER_ID);

            plateUseCases.update(plate);

            verify(platePersistencePort).upsertPlate(plateArgumentCaptor.capture());
            Plate plateUpdated = plateArgumentCaptor.getValue();
            assertEquals(plate.getPrice(), plateUpdated.getPrice());
            assertEquals(existingPlate.getDescription(), plateUpdated.getDescription());
        }

        @Test
        void updatePlatePriceFail() {
            Plate plate = DataDomainFactory.createPlate();
            plate.setPrice(-54000);

            Plate existingPlate = DataDomainFactory.createPlate();

            when(platePersistencePort.getByID(DataConstants.DEFAULT_PLATE_ID))
                    .thenReturn(Optional.of(existingPlate));

            when(restaurantPersistencePort.getById(DataConstants.DEFAULT_RESTAURANT_ID))
                    .thenReturn(Optional.of(DataDomainFactory.createRestaurant()));

            when(authenticationPort.getAuthenticateUserId())
                    .thenReturn(DataConstants.DEFAULT_OWNER_ID);

            DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.update(plate));
            assertEquals(Constants.PRICE_NOT_ALLOWED, exception.getMessage());

        }

        @Test
        void updatePlateFailOwnerIdNotAllowed() {
            Long ownerId = 10L;
            Plate plate = DataDomainFactory.createPlate();
            Plate existingPlate = DataDomainFactory.createPlate();

            when(platePersistencePort.getByID(DataConstants.DEFAULT_PLATE_ID))
                    .thenReturn(Optional.of(existingPlate));

            when(restaurantPersistencePort.getById(DataConstants.DEFAULT_RESTAURANT_ID))
                    .thenReturn(Optional.of(DataDomainFactory.createRestaurant()));

            when(authenticationPort.getAuthenticateUserId())
                    .thenReturn(ownerId);

            DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.update(plate));
            assertEquals(Constants.OWNER_NOT_ALLOWED, exception.getMessage());

        }

        @Test
        void updatePlateFailRestaurantNotFound() {
            Long ownerId = 10L;
            Plate plate = DataDomainFactory.createPlate();
            Plate existingPlate = DataDomainFactory.createPlate();

            when(platePersistencePort.getByID(DataConstants.DEFAULT_PLATE_ID))
                    .thenReturn(Optional.of(existingPlate));

            when(restaurantPersistencePort.getById(DataConstants.DEFAULT_RESTAURANT_ID))
                    .thenReturn(Optional.empty());

            when(authenticationPort.getAuthenticateUserId())
                    .thenReturn(ownerId);

            DomainException exception = assertThrows(DomainException.class, ()-> plateUseCases.update(plate));
            assertEquals(Constants.RESTAURANT_NO_FOUND, exception.getMessage());

        }
    }

    @Nested
    @DisplayName("setStatusPlate")
    class setStatusPlateTests{
        @Test
        void setStatusPlateSuccessful() {
            Plate plate = DataDomainFactory.createPlate();
            plate.setIsActive(false);
            Plate existingPlate = DataDomainFactory.createPlate();

            when(platePersistencePort.getByID(DataConstants.DEFAULT_PLATE_ID))
                    .thenReturn(Optional.of(existingPlate));

            when(restaurantPersistencePort.getById(DataConstants.DEFAULT_RESTAURANT_ID))
                    .thenReturn(Optional.of(DataDomainFactory.createRestaurant()));

            when(authenticationPort.getAuthenticateUserId())
                    .thenReturn(DataConstants.DEFAULT_OWNER_ID);

            plateUseCases.setActive(plate);
            verify(platePersistencePort).upsertPlate(plateArgumentCaptor.capture());
            Plate plateUpdated = plateArgumentCaptor.getValue();
            assertEquals(plate.getIsActive(), plateUpdated.getIsActive());
        }
    }

    @Nested
    @DisplayName("getPlatesByRestaurant")
    class getPlatesByRestaurantTests{
        @Test
        void getPlatesByRestaurantWithCategorySuccessful() {
            Plate plate = DataDomainFactory.createPlate();
            List<Plate> listPlates = List.of(plate, plate, plate);

            when(categoryPersistencePort.getCategoryById(DataConstants.DEFAULT_CATEGORY_ID))
                    .thenReturn(Optional.of(DataDomainFactory.createCategory()));

            when(restaurantPersistencePort.getById(DataConstants.DEFAULT_RESTAURANT_ID))
                    .thenReturn(Optional.of(DataDomainFactory.createRestaurant()));

            when(platePersistencePort.getPlatesByRestaurantId(
                    DataConstants.DEFAULT_RESTAURANT_ID,
                    DataConstants.DEFAULT_PAGE_SIZE,
                    DataConstants.DEFAULT_PAGE,
                    DataConstants.DEFAULT_CATEGORY_ID
            )).thenReturn(listPlates);

            List<Plate> listPlatesFound = plateUseCases.getPlatesByRestaurant(
                    DataConstants.DEFAULT_RESTAURANT_ID,
                    DataConstants.DEFAULT_PAGE_SIZE,
                    DataConstants.DEFAULT_PAGE,
                    DataConstants.DEFAULT_CATEGORY_ID);

            verify(platePersistencePort).getPlatesByRestaurantId(anyLong(), anyInt(), anyInt(), anyLong());
            assertEquals(listPlates.size(), listPlatesFound.size());
        }

        @Test
        void getPlatesByRestaurantWithoutCategorySuccessful() {
            Plate plate = DataDomainFactory.createPlate();
            List<Plate> listPlates = List.of(plate, plate, plate);

            when(restaurantPersistencePort.getById(DataConstants.DEFAULT_RESTAURANT_ID))
                    .thenReturn(Optional.of(DataDomainFactory.createRestaurant()));

            when(platePersistencePort.getPlatesByRestaurantId(
                    DataConstants.DEFAULT_RESTAURANT_ID,
                    DataConstants.DEFAULT_PAGE_SIZE,
                    DataConstants.DEFAULT_PAGE
            )).thenReturn(listPlates);

            List<Plate> listPlatesFound = plateUseCases.getPlatesByRestaurant(
                    DataConstants.DEFAULT_RESTAURANT_ID,
                    DataConstants.DEFAULT_PAGE_SIZE,
                    DataConstants.DEFAULT_PAGE, null);

            verify(platePersistencePort).getPlatesByRestaurantId(anyLong(), anyInt(), anyInt());
            assertEquals(listPlates.size(), listPlatesFound.size());
        }
    }
}