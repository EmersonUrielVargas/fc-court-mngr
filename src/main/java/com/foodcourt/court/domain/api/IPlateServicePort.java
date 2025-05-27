package com.foodcourt.court.domain.api;

import com.foodcourt.court.domain.model.Plate;

import java.util.List;
import java.util.Optional;

public interface IPlateServicePort {
    void create(Plate plate);

    void update(Plate plate);
    void setActive(Plate plate);
    List<Plate> getPlatesByRestaurant(Long restaurantId, Integer pageSize, Integer page, Optional<Long> categoryId);

}
