package com.foodcourt.court.domain.api;

import com.foodcourt.court.domain.model.Plate;

public interface IPlateServicePort {
    void create(Plate plate);

    void update(Plate plate);
    void setActive(Plate plate);
}
