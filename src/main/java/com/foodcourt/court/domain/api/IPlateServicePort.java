package com.foodcourt.court.domain.api;

import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.utilities.CustomPage;

public interface IPlateServicePort {
    void create(Plate plate);

    void update(Plate plate);
    void setActive(Plate plate);
    CustomPage<Plate> getPlatesByRestaurant(Long restaurantId, Integer pageSize, Integer page, Long categoryId);

}
