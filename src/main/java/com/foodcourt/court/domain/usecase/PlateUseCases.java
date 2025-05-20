package com.foodcourt.court.domain.usecase;

import com.foodcourt.court.domain.api.IPlateServicePort;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.domain.exception.DomainException;
import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.spi.ICategoryPersistencePort;
import com.foodcourt.court.domain.spi.IPlatePersistencePort;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import com.foodcourt.court.domain.validators.UtilitiesValidator;
public class PlateUseCases implements IPlateServicePort {

    private final IPlatePersistencePort platePersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;


    public PlateUseCases(IPlatePersistencePort platePersistencePort, IRestaurantPersistencePort restaurantPersistencePort, ICategoryPersistencePort categoryPersistencePort) {
        this.platePersistencePort = platePersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }


    @Override
    public void create(Plate plate) {
        UtilitiesValidator.validatePrice(plate.getPrice());
        categoryPersistencePort.getCategoryById(plate.getCategoryId())
                .orElseThrow(()->new DomainException(Constants.CATEGORY_NO_FOUND));
        restaurantPersistencePort.getById(plate.getRestaurantId())
                .orElseThrow(()->new DomainException(Constants.RESTAURANT_NO_FOUND));
        platePersistencePort.createPlate(plate);
    }
}
