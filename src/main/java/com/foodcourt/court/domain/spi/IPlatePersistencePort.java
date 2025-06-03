package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.utilities.CustomPage;

import java.util.List;
import java.util.Optional;

public interface IPlatePersistencePort {

    void upsertPlate(Plate plate);

    Optional<Plate> getByID(Long plateId);

    CustomPage<Plate> getPlatesByRestaurantId(Long restaurantId, Integer pageSize, Integer page);

    CustomPage<Plate> getPlatesByRestaurantId(Long restaurantId, Integer pageSize, Integer page, Long categoryId);

    List<Long> findExistingPlateIdsInRestaurant(Long restaurantId, List<Long> plateIds);

}
