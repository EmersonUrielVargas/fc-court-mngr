package com.foodcourt.court.domain.usecase;

import com.foodcourt.court.domain.api.IPlateServicePort;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.exception.DomainException;
import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.spi.IAuthenticationPort;
import com.foodcourt.court.domain.spi.ICategoryPersistencePort;
import com.foodcourt.court.domain.spi.IPlatePersistencePort;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import com.foodcourt.court.domain.validators.UtilitiesValidator;
public class PlateUseCases implements IPlateServicePort {

    private final IPlatePersistencePort platePersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IAuthenticationPort authenticationPort;


    public PlateUseCases(IPlatePersistencePort platePersistencePort,
                         IRestaurantPersistencePort restaurantPersistencePort,
                         ICategoryPersistencePort categoryPersistencePort,
                         IAuthenticationPort authenticationPort) {
        this.platePersistencePort = platePersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.authenticationPort = authenticationPort;
    }


    @Override
    public void create(Plate plate) {
        UtilitiesValidator.validatePrice(plate.getPrice());
        categoryPersistencePort.getCategoryById(plate.getCategoryId())
                .orElseThrow(()->new DomainException(Constants.CATEGORY_NO_FOUND));
        validateOwnerRestaurant(plate.getRestaurantId());
        platePersistencePort.upsertPlate(plate);
    }

    @Override
    public void update(Plate plate) {
        Plate existPlate = platePersistencePort.getByID(plate.getId())
                .orElseThrow(()->new DomainException(Constants.PLATE_NO_FOUND));
        validateOwnerRestaurant(existPlate.getRestaurantId());
        existPlate.setDescription(UtilitiesValidator.getDefaultIsNullOrEmpty(plate.getDescription(), existPlate.getDescription()));
        Integer newPrice = UtilitiesValidator.getDefaultIsNullOrEmpty(plate.getPrice(), existPlate.getPrice());
        UtilitiesValidator.validatePrice(newPrice);
        existPlate.setPrice(newPrice);
        platePersistencePort.upsertPlate(existPlate);
    }

    @Override
    public void setActive(Plate plate) {
        Plate existPlate = platePersistencePort.getByID(plate.getId())
                .orElseThrow(()->new DomainException(Constants.PLATE_NO_FOUND));
        validateOwnerRestaurant(existPlate.getRestaurantId());
        existPlate.setIsActive(plate.getIsActive());
        platePersistencePort.upsertPlate(existPlate);
    }

    private void validateOwnerRestaurant(Long restaurantId){
        Long userIdAuthenticated =  authenticationPort.getAuthenticateUserId();
        Restaurant restaurant = restaurantPersistencePort.getById(restaurantId)
                .orElseThrow(()->new DomainException(Constants.RESTAURANT_NO_FOUND));
        if (!restaurant.getOwnerId().equals(userIdAuthenticated)){
            throw new DomainException(Constants.OWNER_NOT_ALLOWED);
        }
    }
}
