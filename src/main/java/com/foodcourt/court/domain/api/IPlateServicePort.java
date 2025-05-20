package com.foodcourt.court.domain.api;

import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.model.Restaurant;

public interface IPlateServicePort {
    void create(Plate plate);
}
